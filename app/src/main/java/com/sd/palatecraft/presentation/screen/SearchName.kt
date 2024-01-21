package com.sd.palatecraft.presentation.screen

import android.annotation.SuppressLint
import android.os.Build
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sd.palatecraft.MainViewModel
import com.sd.palatecraft.presentation.components.listitems.ThumbNailItem
import com.sd.palatecraft.presentation.components.searchbar.SearchBar
import com.sd.palatecraft.presentation.screen.destinations.RecipeScreenDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

private const val TAG = "SearchName"
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun SearchName(navigator: DestinationsNavigator, viewModel: MainViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val recipes by viewModel.recipes.collectAsState(emptyList())
    val isLoading by viewModel.isLoading.collectAsState(true)
    val isError by viewModel.isError.collectAsState(false)
    val message by viewModel.message.collectAsState("")
    val currentQuery by viewModel.currentQuery.collectAsState()
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
                    if(currentQuery.isEmpty()){
                        Text(text = "",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.secondary)
                    }else if(currentQuery.isNotEmpty() && isError){
                        Text(text = "Invalid Letter",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.error)
                    }else if(!isLoading && currentQuery.isNotEmpty()){
                        Text(
                            text = "Results: ${recipes.size}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary)
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                SearchBar(
                    label = "Search by Name",
                    onClearClicked = { viewModel.updateQuery("") },
                    onSearchQueryChanged = { query ->
                        coroutineScope.launch {
                            viewModel.searchByName(query)
                        }
                    }
                )
            }
        }
    ) {
        if (isLoading || currentQuery.isBlank()) {
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
        } else if (isError && currentQuery.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message,
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