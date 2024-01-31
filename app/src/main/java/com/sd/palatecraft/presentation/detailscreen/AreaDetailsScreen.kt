package com.sd.palatecraft.presentation.detailscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sd.palatecraft.MainViewModel
import com.sd.palatecraft.presentation.components.listitems.FilterItem
import com.sd.palatecraft.presentation.destinations.IngredientsScreenDestination
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun AreaDetailsScreen(
    navigator: DestinationsNavigator, areaName: String, viewModel: MainViewModel = getViewModel(),
) {
    val filteredMeals by viewModel.filteredMeals.collectAsState(emptyList())
    val isLoading by viewModel.isLoading.collectAsState(true)
    val isError by viewModel.isError.collectAsState(false)
    val message by viewModel.message.collectAsState("")
    LaunchedEffect(Unit) {
        viewModel.searchByArea(area = areaName)
    }
    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Loading Area",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.height(5.dp)
                )
            }
        }
    } else if (isError) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = message)
        }
    } else {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSecondary)
            ) {
                Spacer(modifier = Modifier.width(18.dp))
                FilledTonalIconButton(modifier = Modifier.weight(0.15f), onClick = {
                    navigator.navigate(
                        IngredientsScreenDestination
                    )
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back To List of Ingredients"
                    )
                }
                Spacer(modifier = Modifier.width(18.dp))
                Text(
                    text = "Area ($areaName)",
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily.Cursive,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .weight(0.75f)
                )
            }
            LazyHorizontalStaggeredGrid(
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                modifier = Modifier.background(color = MaterialTheme.colorScheme.onSecondary),
                rows = StaggeredGridCells.Fixed(2)
            ) {
                items(
                    items = filteredMeals
                ) { meal ->
                    FilterItem(filteredMeal = meal)
                }
            }
        }
    }
}