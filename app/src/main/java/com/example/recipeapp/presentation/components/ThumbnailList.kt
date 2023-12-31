package com.example.recipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.Coil
import coil.compose.AsyncImage
import com.example.recipeapp.remote.Meal
import com.example.recipeapp.ui.theme.Black40
import com.example.recipeapp.ui.theme.BlackA60
import com.example.recipeapp.ui.theme.WhiteA90
import com.example.recipeapp.util.backgrounds
import com.example.recipeapp.util.openCustomTab
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ThumbNailList(recipe: Meal, navigator: DestinationsNavigator) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.inversePrimary, shape = RoundedCornerShape(20.dp))
            .padding(PaddingValues(15.dp, 35.dp, 15.dp, 30.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .aspectRatio(0.5f)
    ){
        val context = LocalContext.current
        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(colors = listOf(Black40, BlackA60)),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(PaddingValues(30.dp, 40.dp, 30.dp, 40.dp))
                .fillMaxWidth()
        ){
            AsyncImage(model = recipe.strMealThumb, contentDescription = "RecipeImg",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(text = recipe.strMeal,
                style=MaterialTheme.typography.titleLarge,
                color= WhiteA90,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.size(15.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "Cruisine: ${recipe.strArea}",
                    style=MaterialTheme.typography.labelLarge,
                    color= WhiteA90,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "Course/Category : ${recipe.strCategory}",
                    style=MaterialTheme.typography.labelLarge,
                    color= WhiteA90,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(25.dp))
            }
            ElevatedButton(onClick = {context.openCustomTab(recipe.strYoutube)},
                modifier = Modifier.shadow(elevation = 8.dp),
                colors = ButtonDefaults.filledTonalButtonColors(Color.Red)
            ) {
                Text(text = "Open in YouTube",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = WhiteA90
                )
            }
            Spacer(modifier = Modifier.size(15.dp))
            FilledTonalButton(onClick = {
                    isSheetOpen = true
            }) {
                Row (verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Instructions")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                }
            }
            if(isSheetOpen){
                ModalBottomSheet(containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.fillMaxWidth(0.95f),
                    onDismissRequest = { isSheetOpen = false }) {
                    val state = rememberScrollState()
                    Column(
                        Modifier
                            .verticalScroll(state)
                            .padding(start = 10.dp, end = 10.dp)
                            .clip(RoundedCornerShape(10.dp))) {
                        Instructions(recipe = recipe)
                        IngredientsList(recipe = recipe)
                    }
                }
            }
        }
    }
}
