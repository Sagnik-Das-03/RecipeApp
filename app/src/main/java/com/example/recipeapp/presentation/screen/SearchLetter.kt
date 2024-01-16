package com.example.recipeapp.presentation.screen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipeapp.presentation.components.searchbar.SearchBar
import com.example.recipeapp.presentation.components.thumnail.ThumbNailItem
import com.example.recipeapp.presentation.screen.destinations.RecipeScreenDestination
import com.example.recipeapp.remote.Meal
import com.example.recipeapp.response.RetrofitInstance
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofLocalizedDateTime
import java.time.format.FormatStyle

private const val TAG = "SearchLetter"

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun SearchLetter(navigator: DestinationsNavigator) {
    var recipes by rememberSaveable { mutableStateOf<List<Meal>>(emptyList()) }
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var isError by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var query by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }
    val dateTime = LocalDateTime.now().format(ofLocalizedDateTime(FormatStyle.MEDIUM))
    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 16.dp, start = 18.dp, end = 8.dp, bottom = 10.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FilledTonalButton(
                        modifier = Modifier
                            .fillMaxWidth(0.2f)
                            .fillMaxHeight(0.06f),
                        onClick = {
                                coroutineScope.launch {
                                    delay(500L)
                                    navigator.navigate(RecipeScreenDestination)
                                }
                             }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "Back to Home",
                            modifier = Modifier
                                .size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    if(query.isEmpty()){
                        Text(text = "",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.secondary)
                    }else if(query.isNotEmpty() && isError){
                        Text(text = "Invalid Letter",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.error)
                    }else{
                        Text(
                            text = "Results: ${recipes.size}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary)
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                SearchBar(
                    label = "Search by Letter",
                    onSearchQueryChanged = {
                        query = it
                        coroutineScope.launch {
                            delay(250L)
                            query.replace(" ", "")
                            if (
                                query.length == 1
                                && !query.matches("[0-9]+".toRegex())
                                && !query.matches("[^a-zA-Z0-9 ]".toRegex())
                            ) {
                                try {
                                    val response = RetrofitInstance.api.getMealByFirstLetter(query)
                                    if (response.isSuccessful) {
                                        recipes = response.body()?.meals ?: emptyList()
                                        Log.i(TAG, "Api called: $dateTime")
                                        isError = false
                                        isLoading = false
                                    } else {
                                        isError = true
                                        errorMsg = "Error Calling API for query $query : $dateTime"
                                        Log.e(TAG, errorMsg)
                                    }
                                } catch (e: HttpException) {
                                    isError = true
                                    errorMsg = "Exception:${e.message} \n time: $dateTime"
                                    Log.e(TAG, errorMsg)
                                } catch (e: SocketTimeoutException) {
                                    isError = true
                                    errorMsg = "Exception:${e.message} \n time: $dateTime"
                                    Log.e(TAG, errorMsg)
                                } finally {
                                    isLoading = false
                                }

                            } else if (
                                query.matches("[0-9]+".toRegex()) || query.matches("[^a-zA-Z0-9 ]".toRegex())
                            ) {
                                isLoading = false
                                isError = true
                                errorMsg = "Search Query: $query is not a Letter"
                                Log.e(TAG, "Error Calling API for query $query : $dateTime")
                            } else if (
                                query.isBlank()
                            ) {
                                isLoading = true
                                Log.e(TAG, "Error Calling API for empty query $query : $dateTime")
                            }else if (
                                query.length>1
                            ) {
                                isError = true
                                isLoading = false
                                errorMsg = "Search Query must be a Single Letter"
                                Log.e(TAG, "Error Calling API for empty query $query : $dateTime")
                            } else {
                                isError = false
                            }
                        }
                    }
                )
            }
        }
    ) {
        if (isLoading || query.isBlank()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Search for a Recipe",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        } else if (isError && query.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = errorMsg,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            LazyRow {
                items(
                    items = recipes

                ) { recipe ->
                    ThumbNailItem(recipe = recipe)
                }
            }
        }
    }
}
