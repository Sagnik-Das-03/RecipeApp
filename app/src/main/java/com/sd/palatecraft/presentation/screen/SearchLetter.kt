package com.sd.palatecraft.presentation.screen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sd.palatecraft.presentation.components.searchbar.SearchBar
import com.sd.palatecraft.presentation.components.listitems.ThumbNailItem
import com.sd.palatecraft.presentation.screen.destinations.RecipeScreenDestination
import com.sd.palatecraft.remote.Meal
import com.sd.palatecraft.response.RetrofitInstance
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sd.palatecraft.RecipeViewModel
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
fun SearchLetter(navigator: DestinationsNavigator, viewModel: RecipeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val recipes by viewModel.recipes.collectAsState(emptyList())
    val isLoading by viewModel.isLoading.collectAsState(true)
    val isError by viewModel.isError.collectAsState(false)
    val message by viewModel.message.collectAsState("")
    val currentQuery by viewModel.currentQuery.collectAsState()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
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
                    if(currentQuery.isEmpty()){
                        Text(text = "",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.secondary)
                    }else if(currentQuery.isNotEmpty() && isError){
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
                    onClearClicked = { viewModel.updateQuery("") },
                    onSearchQueryChanged = {
                        coroutineScope.launch {
                            delay(250L)
                            viewModel.searchByLetter(letter = it)
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
            LazyRow{
                items(
                    items = recipes

                ) { recipe ->
                    ThumbNailItem(recipe = recipe)
                }
            }
        }
    }
}
