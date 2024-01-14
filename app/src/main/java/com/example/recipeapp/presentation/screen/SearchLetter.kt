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
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.recipeapp.presentation.components.ThumbNail
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
    val dateTime = LocalDateTime.now().format(ofLocalizedDateTime(FormatStyle.MEDIUM))
    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 16.dp, start = 18.dp, end = 8.dp, bottom = 10.dp)
            ) {
                FilledTonalButton(
                    onClick = { navigator.navigate(RecipeScreenDestination) }) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "Back to Home",
                        modifier = Modifier
                            .size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                SearchBar(
                    label = "Search by Letter",
                    onSearchQueryChanged = {
                        query = it
                        coroutineScope.launch {
                            delay(500L)
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
                                        Log.e(TAG, "Error Calling API for query $query : $dateTime")
                                    }
                                } catch (e: HttpException) {
                                    isError = true
                                    Log.e(TAG, "Exception:${e.message} \n time: $dateTime")
                                } catch (e: SocketTimeoutException) {
                                    isError = true
                                    Log.e(TAG, "Exception:${e.message} \n time: $dateTime")
                                } finally {
                                    isLoading = false
                                }

                            } else if (
                                query.matches("[0-9]+".toRegex()) || query.matches("[^a-zA-Z0-9 ]".toRegex())
                            ) {
                                isError = true
                                Log.e(TAG, "Error Calling API for query $query : $dateTime")
                            } else if (
                                query.isBlank()
                            ) {
                                isLoading = true
                                Log.e(TAG, "Error Calling API for query $query : $dateTime")
                            }else {
                                isError = false
                            }
                        }
                    }
                )
            }
        }
    ) {
        if (isLoading && query.isBlank()) {
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
        } else if (isError) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Some Unknown Error Occurred",
                    style = MaterialTheme.typography.titleSmall,
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
