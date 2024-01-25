package com.sd.palatecraft.presentation.components.searchbar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sd.palatecraft.MainViewModel
import com.sd.palatecraft.data.remote.dto.FilteredMeals
import com.sd.palatecraft.data.remote.dto.Meal
import com.sd.palatecraft.presentation.destinations.RecipeScreenDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopSearchBar(
    coroutineScope: CoroutineScope,
    navigator: DestinationsNavigator,
    viewModel: MainViewModel,
    currentQuery: String,
    isError: Boolean,
    isLoading: Boolean,
    searchFunction: suspend MainViewModel.() -> Unit,
    recipes: List<Meal>,
    label: String){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 16.dp, start = 18.dp, end = 8.dp, bottom = 10.dp)
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
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Back to Home",
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            SearchBar(
                label = label,
                onClearClicked = { viewModel.updateQuery("") },
                onSearchQueryChanged = { query ->
                    coroutineScope.launch {
                        viewModel.searchFunction()
                    }
                }
            )
        }
        if(currentQuery.isEmpty()){
            Text(text = "",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary)
        }else if(currentQuery.isNotEmpty() && isError){
            Text(text = "Invalid Search",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.error)
        }else if(!isLoading && currentQuery.isNotEmpty()){
            Text(
                text = "Results: ${recipes.size}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopFilterSearchBar(
    coroutineScope: CoroutineScope,
    navigator: DestinationsNavigator,
    viewModel: MainViewModel,
    searchFunction: suspend MainViewModel.() -> Unit,
    currentQuery: String,
    isError: Boolean,
    isLoading: Boolean,
    filteredMealsList: List<FilteredMeals>,
    label: String,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 16.dp, start = 18.dp, end = 8.dp, bottom = 10.dp)
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
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Back to Home",
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            SearchBar(
                label = label,
                onClearClicked = { viewModel.updateQuery("") },
                onSearchQueryChanged = { query ->
                    coroutineScope.launch {
                        viewModel.searchFunction()
                    }
                }
            )
        }
        if(currentQuery.isEmpty()){
            Text(text = "",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary)
        }else if(currentQuery.isNotEmpty() && isError){
            Text(text = "Invalid Search",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.error)
        }else if(!isLoading && currentQuery.isNotEmpty()){
            Text(
                text = "Results: ${filteredMealsList.size}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp))
        }
    }
}