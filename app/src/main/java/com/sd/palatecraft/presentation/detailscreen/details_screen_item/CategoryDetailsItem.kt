package com.sd.palatecraft.presentation.detailscreen.details_screen_item

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sd.palatecraft.MainViewModel
import com.sd.palatecraft.data.remote.dto.FilteredMeals
import com.sd.palatecraft.presentation.components.IngredientsList
import com.sd.palatecraft.presentation.components.Instructions
import com.sd.palatecraft.presentation.components.videoplayer.YoutubePlayer
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailsItem(meal: FilteredMeals, viewModel: MainViewModel = getViewModel()) {
    val width = LocalConfiguration.current.screenWidthDp
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .widthIn(min = (width * 0.6).dp, max = (width * 0.9).dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )){
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = meal.strMeal, textAlign = TextAlign.Center, style = MaterialTheme.typography.titleLarge, modifier = Modifier.offset(y = 16.dp))
            AsyncImage(model = meal.strMealThumb, contentDescription = "Meal Thumbnail",
                modifier = Modifier
                    .scale(0.7f)
                    .clip(shape = RoundedCornerShape(12.dp)))
            FilledTonalButton(
                modifier = Modifier.offset(y = (-20).dp), colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.75f),
                contentColor = MaterialTheme.colorScheme.onPrimary
            ), onClick = { isSheetOpen = true }) {
                Text(text = "Instructions")
                Spacer(modifier = Modifier.width(10.dp))
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Open Instructions")
            }
            Spacer(modifier = Modifier.height(10.dp))
            if(isSheetOpen){
                ModalBottomSheet(onDismissRequest = { isSheetOpen = false }) {
                    val lifecycleOwner = LocalLifecycleOwner.current
                    val meals by viewModel.recipes.collectAsState(emptyList())
                    val isLoading by viewModel.isLoading.collectAsState(true)
                    val isError by viewModel.isError.collectAsState(false)
                    val message by viewModel.message.collectAsState("")
                    LaunchedEffect(Unit){
                        viewModel.searchById(meal.idMeal)
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