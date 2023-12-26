package com.example.recipeapp.presentation.components

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun AddIngredient(ingredient: Any?, measure: Any?) {
    if (ingredient.toString().isNotBlank() && measure.toString().isNotBlank()) {
        if (ingredient != null && measure != null) {
            IngredientsList(ingredient = ingredient, measure = measure)
        }
    }
}
@Composable
fun IngredientsList(ingredient: Any, measure: Any){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp, 0.dp)){
        val url = "https://www.themealdb.com/images/ingredients/${ingredient}.png"
        println(url)
        AsyncImage(model = url, contentDescription = null, modifier = Modifier.size(50.dp))
        Text(text = ingredient.toString(), fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.DarkGray, modifier = Modifier.padding(10.dp,2.dp))
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = measure.toString(), fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.DarkGray, modifier = Modifier.padding(10.dp,2.dp))
    }
}

