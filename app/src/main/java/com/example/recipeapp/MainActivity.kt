package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.enableSavedStateHandles
import com.example.recipeapp.presentation.screen.NavGraphs
import com.example.recipeapp.ui.theme.RecipeAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeAppTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}


