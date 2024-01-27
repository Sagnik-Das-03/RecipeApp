package com.sd.palatecraft.presentation.components.listitems

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.sd.palatecraft.presentation.components.IngredientsList
import com.sd.palatecraft.presentation.components.Instructions
import com.sd.palatecraft.presentation.components.ThumbNail
import com.sd.palatecraft.data.remote.dto.Meal

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecipeItem(recipe: Meal) {
    ThumbNail(recipe = recipe)
    Instructions(recipe= recipe)
    IngredientsList(recipe = recipe)// Add more UI components based on your Recipe data model
}