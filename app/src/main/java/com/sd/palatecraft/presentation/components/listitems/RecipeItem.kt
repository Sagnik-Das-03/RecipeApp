package com.sd.palatecraft.presentation.components.listitems

import androidx.compose.runtime.Composable
import com.sd.palatecraft.presentation.components.IngredientsList
import com.sd.palatecraft.presentation.components.Instructions
import com.sd.palatecraft.presentation.components.ThumbNail
import com.sd.palatecraft.data.remote.Meal

@Composable
fun RecipeItem(recipe: Meal) {
    ThumbNail(recipe = recipe)
    Instructions(recipe= recipe)
    IngredientsList(recipe = recipe)// Add more UI components based on your Recipe data model
}