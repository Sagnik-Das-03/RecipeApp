package com.sd.palatecraft.presentation.components.listitems

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sd.palatecraft.data.remote.dto.FilteredMeals
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sd.palatecraft.MainViewModel
import com.sd.palatecraft.presentation.components.IngredientsList
import com.sd.palatecraft.presentation.components.Instructions
import com.sd.palatecraft.presentation.components.videoplayer.YoutubePlayer
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterItem(navigator: DestinationsNavigator, filteredMeal: FilteredMeals, viewModel:MainViewModel = getViewModel()) {
    var isSheetOpen by remember { mutableStateOf(false) }
    val width = LocalConfiguration.current.screenWidthDp
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .widthIn(min = (width * 0.65).dp, max = (width * 0.85).dp)
            .background(
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 20.dp)
        ) {
            AsyncImage(
                model = filteredMeal.strMealThumb,
                contentDescription = "Thumbnail",
                modifier = Modifier
                    .size((0.55 * width).dp)
                    .clip(RoundedCornerShape(16.dp)))
            Text(
                text = filteredMeal.strMeal,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            FilledTonalButton(
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.75f),
                    contentColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                onClick = { isSheetOpen = true }) {
                Text(text = "Instructions")
                Spacer(modifier = Modifier.height(10.dp))
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Go to",
                    modifier = Modifier.size(18.dp))
            }
            if(isSheetOpen){
                ModalBottomSheet(onDismissRequest = { isSheetOpen = false }) {
                    val lifecycleOwner = LocalLifecycleOwner.current
                    val meals by viewModel.recipes.collectAsState(emptyList())
                    val isLoading by viewModel.isLoading.collectAsState(true)
                    val isError by viewModel.isError.collectAsState(false)
                    val message by viewModel.message.collectAsState("")
                    LaunchedEffect(Unit){
                        viewModel.searchById(filteredMeal.idMeal)
                    }
                    if(isLoading){
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            CircularProgressIndicator()
                            Text(text = "Loading", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.outline)
                        }
                    }else if (isError){
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            Text(text = message, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }else {
                        LazyColumn{
                            items(
                                items = meals
                            ){ selectedMeal ->
                                Text(
                                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f),
                                    text = "Recipe: ${selectedMeal.strMeal}",
                                    style = MaterialTheme.typography.headlineMedium.copy(fontStyle = FontStyle.Italic),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp, horizontal = 16.dp)
                                        .fillMaxWidth())
                                Spacer(modifier = Modifier.height(8.dp))
                                YoutubePlayer(
                                    youtubeVideoId = selectedMeal.strYoutube.substringAfter("="),
                                    lifecycleOwner = lifecycleOwner)
                                Instructions(recipe = selectedMeal)
                                IngredientsList(recipe = selectedMeal)
                            }
                        }
                    }
                }
            }
        }
    }
}