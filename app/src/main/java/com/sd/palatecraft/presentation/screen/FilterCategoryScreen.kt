package com.sd.palatecraft.presentation.screen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import com.sd.palatecraft.presentation.components.listitems.FilterCategoryItem
import com.sd.palatecraft.presentation.components.searchbar.SearchBar
import com.sd.palatecraft.presentation.screen.destinations.RecipeScreenDestination
import com.sd.palatecraft.remote.FilteredMeals
import com.sd.palatecraft.response.RetrofitInstance
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private const val TAG = "FilterCategoryScreen"
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun FilterCategoryScreen(navigator: DestinationsNavigator) {
    var filteredMeals by rememberSaveable { mutableStateOf<List<FilteredMeals>>(emptyList()) }
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var isError by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var query by remember { mutableStateOf("Seafood") }
    var errorMsg by remember { mutableStateOf("") }
    val dateTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
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
                    }else if(!isLoading && query.isNotEmpty()){
                        Text(
                            text = "Results: ${filteredMeals.size}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary)
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                SearchBar(
                    label = "Search by Category",
                    onClearClicked = { query = "" },
                    onSearchQueryChanged = {
                        query = it
                        coroutineScope.launch {
                            delay(5000L)
                            if (
                                query.length > 1
                                && !query.contains(regex = "[0-9]+".toRegex())
                                && !query.contains(regex = "[^a-zA-Z0-9 ]".toRegex())
                            ) {
                                try {
                                    val response = RetrofitInstance.api.filterByCategory(query)
                                    if (response.isSuccessful) {
                                        filteredMeals = response.body()?.meals ?: emptyList()
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
                                query.isBlank()
                            ) {
                                isLoading = true
                                Log.e(TAG, "Error Calling API for empty blank query $query : $dateTime")
                            }else if (
                                query.contains(regex = "[0-9]+".toRegex()) || query.contains(regex = "[^a-zA-Z0-9 ]".toRegex())
                            ) {
                                isError = true
                                isLoading = false
                                errorMsg = "Search Query: $query is not a Word"
                                Log.e(TAG, "Error Calling API for query $query : $dateTime")
                            } else {
                                isError = false
                            }
                        }
                    }
                )
            }
        }
    ){
        if (isLoading || query.isBlank()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Search for a Category",
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
            LazyColumn(
                contentPadding = PaddingValues(vertical = 4.dp, horizontal = 4.dp),
                modifier = Modifier.padding(top = 85.dp)
            ){
                items(
                    items = filteredMeals
                ){ filteredMeal->
                    FilterCategoryItem(navigator = navigator, filteredMeal = filteredMeal)
                }
            }
        }
    }
}