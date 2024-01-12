package com.example.recipeapp.presentation.components

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipeapp.ui.theme.BlackA60

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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.inverseOnSurface,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        val url = "https://www.themealdb.com/images/ingredients/${ingredient}.png"
        AsyncImage(model = url, contentDescription = "Ingredient Image", modifier = Modifier
            .size(50.dp)
            .padding(10.dp))
        Spacer(modifier = Modifier.width(8.dp)) // Adjust the spacing as needed
        Text(
            text = ingredient.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(10.dp, 10.dp)
        )
        Spacer(modifier = Modifier.width(8.dp)) // Adjust the spacing as needed
        Text(
            text = measure.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(10.dp, 10.dp),
            textAlign = TextAlign.Center,
        )
    }
    Spacer(modifier = Modifier.size(10.dp))

}



