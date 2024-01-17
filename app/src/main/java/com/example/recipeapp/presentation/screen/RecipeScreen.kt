package com.example.recipeapp.presentation.screen

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.presentation.components.IngredientsList
import com.example.recipeapp.presentation.components.Instructions
import com.example.recipeapp.presentation.components.ThumbNail
import com.example.recipeapp.presentation.components.navdrawer.components.NavigationDrawer
import com.example.recipeapp.remote.Meal
import com.example.recipeapp.response.RetrofitInstance
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofLocalizedDateTime
import java.time.format.FormatStyle

private const val TAG = "HomeScreen"
@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@RootNavGraph(start = true)
@Destination
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipeScreen(navigator: DestinationsNavigator) {
    var recipes by rememberSaveable { mutableStateOf<List<Meal>>(emptyList()) }
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var isError by rememberSaveable { mutableStateOf(false) }
    var initialApiCalled by rememberSaveable { mutableStateOf(false) }
    val dateTime = LocalDateTime.now().format(ofLocalizedDateTime(FormatStyle.MEDIUM))
    LaunchedEffect(Unit) {
        delay(250L)
        if (!initialApiCalled) {
            try {
                val response = RetrofitInstance.api.getRandomRecipe()
                if (response.isSuccessful) {
                    recipes = response.body()?.meals ?: emptyList()
                    Log.i(TAG, "API called: $dateTime")
                } else {
                    isError = true
                }
            } catch (e: HttpException) {
                isError = true
                Log.e(TAG, "Exception:${e.message} \n date: $dateTime")
            }catch (e: SocketTimeoutException) {
                isError = true
                Log.e(TAG, "Exception:${e.message} \n date: $dateTime")
            } finally {
                isLoading = false
                initialApiCalled = true
            }
        }
    }
    // Swipe-to-refresh state
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            try {
                val response = RetrofitInstance.api.getRandomRecipe()
                if (response.isSuccessful) {
                    recipes = response.body()?.meals ?: emptyList()
                } else {
                    isError = true
                }
            } catch (e: HttpException) {
                isError = true
                print(e.printStackTrace())
            } finally {
                isLoading = false
                isRefreshing = false
            }
        }
    }

    // SwipeRefresh composable
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            // Set the refreshing state to true to trigger the refresh
            isRefreshing = true
        },
    ) {
        Column {
            if (isLoading) {
                // Display loading indicator
                Column (modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "Loading Recipes...",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.size(40.dp))
                    CircularProgressIndicator(modifier = Modifier.size(40.dp), strokeWidth = 5.dp)
                }
            } else if (isError) {
                // Display error message
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Error Fetching Data", color = Color.Red, fontSize = 60.sp)
                }
            } else {
                NavigationDrawer(navigator= navigator, recipes = recipes)
            }
        }
    }
}

