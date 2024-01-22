package com.sd.palatecraft.presentation.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sd.palatecraft.R
import com.sd.palatecraft.MainViewModel
import com.sd.palatecraft.presentation.components.navdrawer.components.NavigationDrawer

@RequiresApi(Build.VERSION_CODES.O)
@RootNavGraph(start = true)
@Destination
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipeScreen(navigator: DestinationsNavigator, viewModel: MainViewModel = viewModel()) {
    val recipes by viewModel.recipes.collectAsState(emptyList())
    val isLoading by viewModel.isLoading.collectAsState(true)
    val isError by viewModel.isError.collectAsState(false)
    LaunchedEffect(Unit){
        viewModel.getRandomRecipe()
    }
    
    // SwipeRefresh composable
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = {
            // Set the refreshing state to true to trigger the refresh
            viewModel.refreshRandomRecipe(isRefreshing = true)
        },
    ) {
        Column {
            if (isLoading) {
                // Display loading indicator
                Column (modifier = Modifier
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_bg),
                        contentDescription = "Icon",
                        modifier = Modifier.scale(1.5f))
                    Spacer(modifier = Modifier.size(80.dp))
                    Text(
                        text = "Loading Recipes...",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.size(40.dp))
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(40.dp), strokeWidth = 5.dp)
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

