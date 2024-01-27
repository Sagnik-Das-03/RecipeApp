package com.sd.palatecraft.presentation.screens

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sd.palatecraft.MainViewModel
import com.sd.palatecraft.presentation.components.listitems.AreasItem
import com.sd.palatecraft.presentation.destinations.RecipeScreenDestination
import com.sd.palatecraft.util.WithAnimation
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import org.koin.androidx.compose.getViewModel

@OptIn(FlowPreview::class)
@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun AreasScreen(navigator: DestinationsNavigator, viewModel: MainViewModel = getViewModel()) {
    val areas by viewModel.areas.collectAsState(emptyList())
    val isLoading by viewModel.isLoading.collectAsState(true)
    val isError by viewModel.isError.collectAsState(false)
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val scrollPosition = prefs.getInt("scroll_position_a", 0)
    val lazyGridState = rememberLazyStaggeredGridState(
        initialFirstVisibleItemIndex = scrollPosition
    )
    LaunchedEffect(lazyGridState) {
        snapshotFlow {
            lazyGridState.firstVisibleItemIndex
        }
            .debounce(500L)
            .collectLatest { index ->
                prefs.edit()
                    .putInt("scroll_position_a", index)
                    .apply()
            }
    }
    LaunchedEffect(Unit) {
        viewModel.listAreas()
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
                    text = "Loading Areas",
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
            Text(text = "Error Fetching Data")
        }
    } else {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.onSecondary)
            ) {
                FilledTonalButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { navigator.navigate(RecipeScreenDestination) }) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Back to Home"
                    )
                }
                Text(
                    text = "List of Area",
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Cursive,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                )
            }
            WithAnimation(animation = expandVertically()+ fadeIn(), delay = 250) {
                LazyVerticalStaggeredGrid(
                    state = lazyGridState,
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.onSecondary)
                ) {
                    items(
                        items = areas
                    ) { area ->

                        AreasItem(area = area)
                    }
                }
            }
        }
    }
}

