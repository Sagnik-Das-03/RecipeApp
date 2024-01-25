package com.sd.palatecraft.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
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
import com.sd.palatecraft.presentation.components.listitems.FilterItem
import com.sd.palatecraft.presentation.components.searchbar.TopFilterSearchBar
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun FilterIngredientScreen(navigator: DestinationsNavigator, viewModel: MainViewModel = getViewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val filteredMeals by viewModel.filteredMeals.collectAsState(emptyList())
    val isLoading by viewModel.isLoading.collectAsState(true)
    val isError by viewModel.isError.collectAsState(false)
    val message by viewModel.message.collectAsState("")
    val currentQuery by viewModel.currentQuery.collectAsState()
    Scaffold(
        topBar = {
            TopFilterSearchBar(
                coroutineScope = coroutineScope,
                navigator = navigator,
                viewModel = viewModel,
                currentQuery = currentQuery,
                isError = isError,
                isLoading = isLoading,
                filteredMealsList = filteredMeals,
                label = "Search for a Ingredient",
                searchFunction = {
                    searchByIngredient(ingredient = currentQuery)
                }
            )
        }
    ){
        if (isLoading || currentQuery.isBlank()) {
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
            LazyHorizontalStaggeredGrid(rows = StaggeredGridCells.Fixed(count = 1),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                modifier = Modifier.padding(top = 120.dp, bottom = 120.dp)
            ){
                items(
                    items = filteredMeals
                ){ filteredMeal->
                    FilterItem(navigator = navigator, filteredMeal = filteredMeal)
                }
            }
        }
    }
}