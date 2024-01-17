package com.example.recipeapp.presentation.components.listitems

import androidx.compose.runtime.Composable
import com.example.recipeapp.presentation.components.IngredientsList
import com.example.recipeapp.presentation.components.Instructions
import com.example.recipeapp.presentation.components.ThumbNail
import com.example.recipeapp.remote.Meal

@Composable
fun RecipeItem(recipe: Meal) {
    ThumbNail(recipe = recipe)
    Instructions(recipe= recipe)
    IngredientsList(recipe = recipe)// Add more UI components based on your Recipe data model
}