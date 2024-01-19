package com.sd.palatecraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sd.palatecraft.presentation.screen.NavGraphs
import com.sd.palatecraft.ui.theme.RecipeAppTheme
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


