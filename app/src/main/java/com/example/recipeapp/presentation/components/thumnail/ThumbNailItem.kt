package com.example.recipeapp.presentation.components.thumnail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipeapp.presentation.components.IngredientsList
import com.example.recipeapp.presentation.components.Instructions
import com.example.recipeapp.remote.Meal
import com.example.recipeapp.ui.theme.WhiteA90
import com.example.recipeapp.util.openCustomTab

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ThumbNailItem(recipe: Meal) {
    var isSheetOpen by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val width = LocalConfiguration.current.screenWidthDp
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(top = 100.dp, start = 10.dp, end = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(PaddingValues(start = 20.dp, end = 20.dp, top = 40.dp, bottom = 30.dp))
                .fillMaxWidth()
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
                    text = "Cruisine: ${recipe.strArea}",
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
            ElevatedButton(
                onClick = { context.openCustomTab(recipe.strYoutube) },
                modifier = Modifier.shadow(
                    elevation = 8.dp,
                    ambientColor = Color.Red,
                    spotColor = Color.Red
                ),
                colors = ButtonDefaults.filledTonalButtonColors(Color.Red)
            ) {
                Text(
                    text = "Open in YouTube",
                    color = MaterialTheme.colorScheme.onTertiary
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.TwoTone.PlayArrow,
                    contentDescription = "Open Instruction",
                    tint = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedButton(
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
                    contentDescription = "Open Instruction",
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
                            Instructions(recipe = recipe)
                            IngredientsList(recipe = recipe)
                        }
                    }
                }
            }
        }
    }
}
