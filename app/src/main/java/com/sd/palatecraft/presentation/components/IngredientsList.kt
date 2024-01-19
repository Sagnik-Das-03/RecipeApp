package com.sd.palatecraft.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sd.palatecraft.remote.Meal

@Composable
fun IngredientsList(recipe: Meal) {
   Surface {
       Column(
           modifier = Modifier
               .background(
                   brush = Brush.linearGradient(colors = listOf(MaterialTheme.colorScheme.inverseOnSurface, MaterialTheme.colorScheme.surface)),
                   shape = RoundedCornerShape(10.dp)
               )
               .padding(PaddingValues(35.dp, 30.dp))
       ) {
           Spacer(modifier = Modifier.size(10.dp))
           Text(text = "List of Ingredients",
               fontStyle = FontStyle.Italic,
               fontWeight = FontWeight.Bold,
               fontSize = 30.sp,
               fontFamily = FontFamily.SansSerif,
               color = MaterialTheme.colorScheme.secondary,
               modifier = Modifier.padding(horizontal = 10.dp))
           Divider(thickness = 3.dp, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(10.dp, 15.dp))
           AddIngredient(ingredient = recipe.strIngredient1, measure = recipe.strMeasure1)
           AddIngredient(ingredient = recipe.strIngredient2, measure = recipe.strMeasure2)
           AddIngredient(ingredient = recipe.strIngredient3, measure = recipe.strMeasure3)
           AddIngredient(ingredient = recipe.strIngredient4, measure = recipe.strMeasure4)
           AddIngredient(ingredient = recipe.strIngredient5, measure = recipe.strMeasure5)
           AddIngredient(ingredient = recipe.strIngredient6, measure = recipe.strMeasure6)
           AddIngredient(ingredient = recipe.strIngredient7, measure = recipe.strMeasure7)
           AddIngredient(ingredient = recipe.strIngredient8, measure = recipe.strMeasure8)
           AddIngredient(ingredient = recipe.strIngredient9, measure = recipe.strMeasure9)
           AddIngredient(ingredient = recipe.strIngredient10, measure = recipe.strMeasure10)
           AddIngredient(ingredient = recipe.strIngredient11, measure = recipe.strMeasure11)
           AddIngredient(ingredient = recipe.strIngredient12, measure = recipe.strMeasure12)
           AddIngredient(ingredient = recipe.strIngredient13, measure = recipe.strMeasure13)
           AddIngredient(ingredient = recipe.strIngredient14, measure = recipe.strMeasure14)
           AddIngredient(ingredient = recipe.strIngredient15, measure = recipe.strMeasure15)
           AddIngredient(ingredient = recipe.strIngredient16, measure = recipe.strMeasure16)
           AddIngredient(ingredient = recipe.strIngredient17, measure = recipe.strMeasure17)
           AddIngredient(ingredient = recipe.strIngredient18, measure = recipe.strMeasure18)
           AddIngredient(ingredient = recipe.strIngredient19, measure = recipe.strMeasure19)
           AddIngredient(ingredient = recipe.strIngredient20, measure = recipe.strMeasure20)
       }
   }
}