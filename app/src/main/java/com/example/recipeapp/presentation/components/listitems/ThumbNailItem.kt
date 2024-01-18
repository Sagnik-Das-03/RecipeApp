package com.example.recipeapp.presentation.components.listitems

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
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
import com.example.recipeapp.presentation.components.IngredientsList
import com.example.recipeapp.presentation.components.Instructions
import com.example.recipeapp.presentation.components.videoplayer.YoutubePlayer
import com.example.recipeapp.remote.Meal
import com.example.recipeapp.ui.theme.WhiteA90

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ThumbNailItem(recipe: Meal) {
    var isSheetOpen by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val width = LocalConfiguration.current.screenWidthDp
    val lifecycleOwner = LocalLifecycleOwner.current
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(top = 100.dp, start = 10.dp, end = 10.dp)
            .fillMaxHeight(),
        contentAlignment = Alignment.TopCenter
    ) {
        val context = LocalContext.current
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 50.dp))
        ) {
            Spacer(modifier = Modifier.size(30.dp))
            AsyncImage(
                model = recipe.strMealThumb, contentDescription = "RecipeImg",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = recipe.strMeal,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                lineHeight = 35.sp,
                color = WhiteA90,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(
                    min= ((width*0.7).toInt().dp),
                    max = ((width*0.9).toInt().dp))
            )
            Spacer(modifier = Modifier.size(15.dp))
            Column {
                Text(
                    text = "Cuisine : ${recipe.strArea}",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = WhiteA90
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = "Course/Category : ${recipe.strCategory}",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = WhiteA90
                )
                Spacer(modifier = Modifier.size(25.dp))
            }
            Spacer(modifier = Modifier.size(10.dp))
            FilledTonalButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                onClick = { isSheetOpen = true }) {
                Text(
                    text = "Open Instructions",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.TwoTone.Add,
                    contentDescription = "Open Details",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(18.dp)
                )
                if(isSheetOpen){
                    ModalBottomSheet(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        modifier = Modifier
                            .padding(start = 15.dp, end = 15.dp),
                        onDismissRequest = { isSheetOpen = false }
                    ) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(scrollState)
                                .padding(10.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                        ) {
                            Text(text = "Tutorial",
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                fontFamily = FontFamily.SansSerif,
                                color = MaterialTheme.colorScheme.secondary)
                            Divider(thickness = 3.dp, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(0.dp, 15.dp))
                            Spacer(modifier = Modifier.height(4.dp))
                            YoutubePlayer(youtubeVideoId = recipe.strYoutube.substringAfter("="), lifecycleOwner = lifecycleOwner)
                            Spacer(modifier = Modifier.height(16.dp))
                            Instructions(recipe = recipe)
                            IngredientsList(recipe = recipe)
                        }
                    }
                }
            }
        }
    }
}
