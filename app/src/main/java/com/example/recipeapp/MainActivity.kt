package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import com.example.recipeapp.presentation.NavGraphs
import com.example.recipeapp.presentation.RecipeScreen
import com.example.recipeapp.presentation.components.ThumbNail
import com.example.recipeapp.ui.theme.RecipeAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

class MainActivity : ComponentActivity() {
    @OptIn(FlowPreview::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeAppTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}


