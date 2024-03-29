package com.sd.palatecraft.presentation.components.listitems

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sd.palatecraft.MainViewModel
import com.sd.palatecraft.data.local.MealEntity
import com.sd.palatecraft.presentation.components.IngredientsList
import com.sd.palatecraft.presentation.components.Instructions
import com.sd.palatecraft.presentation.components.videoplayer.YoutubePlayer
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThumbNailDBItem(recipe: MealEntity, isError: Boolean, viewModel: MainViewModel = getViewModel()) {
    var isSheetOpen by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val width = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(top = 140.dp, start = 16.dp, end = 8.dp, bottom = 60.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(PaddingValues(start = 15.dp, end = 10.dp, top = 20.dp, bottom = 20.dp))
        ) {
            AsyncImage(
                model = recipe.strMealThumb, contentDescription = "RecipeImg",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = recipe.strMeal,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = FontFamily.SansSerif,
                lineHeight = 35.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Start,
                modifier = Modifier.widthIn(
                    min= ((width*0.65).toInt().dp),
                    max = ((width*0.75).toInt().dp))
            )
            Spacer(modifier = Modifier.size(15.dp))
            Column {
                Text(
                    text = "Cuisine : ${recipe.strArea}",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.labelLarge,
                    fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = "Course/Category : ${recipe.strCategory}",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.labelLarge,
                    fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.size(25.dp))
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilledTonalButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.75f),
                        contentColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    onClick = { isSheetOpen = true }) {
                    Text(
                        text = "Open Instructions")
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.TwoTone.Add,
                        contentDescription = "Open Details",
                        modifier = Modifier.size(18.dp)
                    )
                    if(isSheetOpen){
                        ModalBottomSheet(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier
                                .padding(start = 15.dp, end = 15.dp),
                            onDismissRequest = { isSheetOpen = false }
                        ) {
                            Column(
                                modifier = Modifier
                                    .verticalScroll(scrollState)
                                    .clip(shape = RoundedCornerShape(10.dp))
                            ) {
                                if(!isError){
                                    Text(text = "Tutorial",
                                        fontStyle = FontStyle.Italic,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 30.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        color = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.padding(horizontal = 32.dp))
                                    Divider(thickness = 3.dp, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(32.dp, 15.dp))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    YoutubePlayer(youtubeVideoId = recipe.strYoutube.substringAfter("="), lifecycleOwner = lifecycleOwner)
                                } else{
                                    Toast.makeText(context, "Cant load Youtube Player in offline mode", Toast.LENGTH_SHORT).show()
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Instructions(recipe = recipe.toMeal())
                                IngredientsList(recipe = recipe.toMeal())
                            }
                        }
                    }
                }
                IconButton(onClick = { viewModel.deleteMeal(meal = recipe).also {
                    Toast.makeText(context, "Recipe removed from Bookmarks", Toast.LENGTH_SHORT).show()
                } }) {
                    Icon(imageVector = Icons.TwoTone.Delete, contentDescription = "Delete from Bookmark")
                }
            }

        }
    }
}
