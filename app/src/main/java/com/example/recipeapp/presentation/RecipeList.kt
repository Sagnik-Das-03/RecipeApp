package com.example.recipeapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.presentation.components.Instructions
import com.example.recipeapp.presentation.components.ThumbNail
import com.example.recipeapp.remote.Meal
import com.example.recipeapp.response.RetrofitInstance
import com.example.recipeapp.ui.theme.Black40
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import retrofit2.HttpException

@Composable
fun RecipeList() {
    var recipes by rememberSaveable { mutableStateOf<List<Meal>>(emptyList()) }
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var isError by rememberSaveable { mutableStateOf(false) }
    var initialApiCalled by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (!initialApiCalled) {
            try {
                val response = RetrofitInstance.api.getRandomRecipe()
                if (response.isSuccessful) {
                    recipes = response.body()?.meals ?: emptyList()
                } else {
                    isError = true
                }
            } catch (e: HttpException) {
                isError = true
            } finally {
                isLoading = false
                initialApiCalled = true
            }
        }
    }
    // Swipe-to-refresh state
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            try {
                val response = RetrofitInstance.api.getRandomRecipe()
                if (response.isSuccessful) {
                    recipes = response.body()?.meals ?: emptyList()
                } else {
                    isError = true
                }
            } catch (e: HttpException) {
                isError = true
                print(e.printStackTrace())
            } finally {
                isLoading = false
                isRefreshing = false
            }
        }
    }

    // SwipeRefresh composable
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            // Set the refreshing state to true to trigger the refresh
            isRefreshing = true
        },
    ) {
        Column {
            if (isLoading) {
                // Display loading indicator
                Column (modifier = Modifier
                    .background(Black40)
                    .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "Loading Recipes...",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                    Spacer(modifier = Modifier.size(40.dp))
                    CircularProgressIndicator(modifier = Modifier.size(40.dp), strokeWidth = 5.dp)
                    coroutineScope.cancel()
                }
            } else if (isError) {
                // Display error message
                Text(text = "Error fetching data", color = Color.Red)
            } else {
                // Display the list of recipes
                LazyColumn {
                    items(recipes) { recipe ->
                        RecipeItem(recipe)
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: Meal) {
    // Compose UI for displaying a single recipe item
    val context = LocalContext.current
    ThumbNail(recipe)
    Instructions(recipe)
    // Add more UI components based on your Recipe data model
}


@Preview(showBackground = true)
@Composable
fun PreviewRecipeList() {
    RecipeList()
}


