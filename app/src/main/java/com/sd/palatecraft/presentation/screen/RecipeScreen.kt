package com.sd.palatecraft.presentation.screen

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.sd.palatecraft.presentation.destinations.RecipeScreenDestination
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@RootNavGraph(start = true)
@Destination
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipeScreen(navigator: DestinationsNavigator, viewModel: MainViewModel = getViewModel()) {
    val recipes by viewModel.recipes.collectAsState(emptyList())
    val isLoading by viewModel.isLoading.collectAsState(true)
    val isError by viewModel.isError.collectAsState(false)
    val message by viewModel.message.collectAsState("")
    val context = LocalContext.current
    LaunchedEffect(Unit) {
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
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_bg),
                        contentDescription = "Icon",
                        modifier = Modifier.scale(1.5f)
                    )
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
                        modifier = Modifier.size(40.dp), strokeWidth = 5.dp
                    )
                }
            } else if (isError) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = message,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier
                                .padding(vertical = 16.dp, horizontal = 16.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.errorContainer,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        )
                        Toast.makeText(
                            context,
                            "Please Check Your Internet Connection",
                            Toast.LENGTH_LONG
                        ).show()
                        FilledTonalButton(onClick = {
                            navigator.navigate(RecipeScreenDestination)
                        }) {
                            Text(text = "Refresh")
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = "Refresh",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

            } else {
                NavigationDrawer(navigator = navigator, recipes = recipes)
            }
        }
    }
}

