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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipeapp.R
import com.example.recipeapp.remote.Meal
import com.example.recipeapp.ui.theme.Black40
import com.example.recipeapp.ui.theme.BlackA60
import com.example.recipeapp.ui.theme.WhiteA90
import com.example.recipeapp.util.backgrounds
import com.example.recipeapp.util.openCustomTab

@Composable
fun ThumbNail(recipe: Meal) {
    val background = backgrounds.random()
    Box(
        modifier = Modifier
            .aspectRatio(0.5f)
            .paint(
                painter = painterResource(id = background),
                contentScale = ContentScale.Crop,
            )
            .padding(PaddingValues(15.dp, 85.dp, 15.dp, 30.dp)),
        contentAlignment = Alignment.TopCenter
    ){
        val context = LocalContext.current
        val scrollState = rememberScrollState()
        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(colors = listOf(Black40, BlackA60)),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(PaddingValues(20.dp, 20.dp, 10.dp, 20.dp))
                .fillMaxWidth()
        ){
            Text(text = "Recipe of the Day",
                fontSize =45.sp,
                fontWeight = FontWeight.ExtraBold,
                color = WhiteA90,
                modifier = Modifier
                    .padding(10.dp),
                fontFamily = FontFamily.Cursive,
                fontStyle = FontStyle.Italic,
            )
            Spacer(modifier = Modifier.size(30.dp))
            AsyncImage(model = recipe.strMealThumb, contentDescription = "RecipeImg",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = recipe.strMeal,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                color = WhiteA90
            )
            Spacer(modifier = Modifier.size(15.dp))
            Column{
                Text(text = "Cruisine: ${recipe.strArea}",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = WhiteA90)
                Spacer(modifier = Modifier.size(5.dp))
                Text(text = "Course/Category : ${recipe.strCategory}",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = WhiteA90)
                Spacer(modifier = Modifier.size(25.dp))
            }
            ElevatedButton(onClick = {context.openCustomTab(recipe.strYoutube)},
                modifier = Modifier.shadow(elevation = 8.dp, ambientColor = Color.Red, spotColor = Color.Red),
                colors = ButtonDefaults.filledTonalButtonColors(Color.Red)
            ) {
                Text(text = "Open in YouTube",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = WhiteA90)
            }
            Spacer(modifier = Modifier.size(25.dp))
            Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.offset(10.dp)){
                Text(text = "Swipe Up for Instructions",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = WhiteA90)
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_up_foreground),
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .aspectRatio(1f))
            }
        }

    }

}


