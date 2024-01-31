package com.sd.palatecraft.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sd.palatecraft.MainViewModel
import com.sd.palatecraft.presentation.components.listitems.ThumbNailDBItem
import com.sd.palatecraft.presentation.destinations.RecipeScreenDestination
import com.sd.palatecraft.util.WithAnimation
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun StarredScreen(navigator: DestinationsNavigator, viewModel: MainViewModel = getViewModel(), isError: Boolean) {
    val meals by viewModel.meals.collectAsState()
    Scaffold(
        topBar = {
            WithAnimation(animation = slideInHorizontally()+ fadeIn(), delay = 250) {
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
                            contentDescription = "Back to Home")
                    }
                    Text(
                        text = "Saved Recipes",
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Cursive,
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
                }
            }
        },
    ) {
        if(meals.isEmpty()){
            Surface(modifier = Modifier.fillMaxSize()) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = "No Recipes in Bookmarks", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.titleLarge)
                }
            }
        }else{
            LazyRow{
                items(
                    items = meals
                ){ meal ->
                    WithAnimation(animation = slideInHorizontally()+ fadeIn(), delay = 300) {
                        ThumbNailDBItem(recipe = meal, isError= isError)
                    }
                }
            }
        }
        
    }

}
