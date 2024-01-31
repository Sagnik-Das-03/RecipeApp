package com.sd.palatecraft.presentation.components.listitems

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sd.palatecraft.MainViewModel
import com.sd.palatecraft.R
import com.sd.palatecraft.data.remote.dto.FilteredMeals
import com.sd.palatecraft.presentation.components.IngredientsList
import com.sd.palatecraft.presentation.components.Instructions
import com.sd.palatecraft.presentation.components.videoplayer.YoutubePlayer
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterItem(filteredMeal: FilteredMeals, viewModel:MainViewModel = getViewModel()) {
    val context = LocalContext.current
    var isSheetOpen by remember { mutableStateOf(false) }
    val width = LocalConfiguration.current.screenWidthDp
    val height = LocalConfiguration.current.screenHeightDp
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .heightIn(min = (0.35 * height).dp, max = (0.5 * height).dp)
            .widthIn(min = (width * 0.65).dp, max = (width * 0.75).dp)
            .background(
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 20.dp)
        ) {
            AsyncImage(
                model = filteredMeal.strMealThumb,
                contentDescription = "Thumbnail",
                modifier = Modifier
                    .size((0.35 * width).dp)
                    .clip(RoundedCornerShape(16.dp)))
            Text(
                text = filteredMeal.strMeal,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FilledTonalButton(
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.75f),
                        contentColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    modifier = Modifier
                        .scale(0.9f)
                        .width(width = (width * 0.5).dp),
                    onClick = { isSheetOpen = true }) {
                    Text(text = "Open Recipe")
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
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    ) {
                                        Text(
                                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f),
                                            text = selectedMeal.strMeal,
                                            textAlign = TextAlign.Center,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.headlineSmall.copy(fontStyle = FontStyle.Italic),
                                            fontWeight = FontWeight.ExtraBold,
                                            modifier = Modifier.weight(0.8f)
                                                .padding(vertical = 8.dp, horizontal = 16.dp))
                                        IconButton(modifier = Modifier.weight(0.2f),
                                            onClick = {
                                            Toast.makeText(
                                                context,
                                                "Recipe Added to Bookmarks",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            viewModel.addMeal(meal = selectedMeal.toMealEntity())
                                        }) {
                                            Icon(painter = painterResource(id = R.drawable.bookmark_solid),
                                                contentDescription = "Add to Bookmark",
                                                modifier = Modifier.size(18.dp),
                                                tint = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f))
                                        }
                                    }
                                    Row(horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 32.dp)) {
                                        Text(text = "Category: ${selectedMeal.strCategory}",
                                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f),
                                            style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.ExtraBold)
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(text ="Area: ${selectedMeal.strArea}",
                                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f),
                                            style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.ExtraBold)
                                    }
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
}