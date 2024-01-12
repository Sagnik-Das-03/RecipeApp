package com.example.recipeapp.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.recipeapp.presentation.components.ThumbNail
import com.example.recipeapp.presentation.screen.destinations.RecipeScreenDestination
import com.example.recipeapp.remote.Meal
import com.example.recipeapp.response.RetrofitInstance
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import retrofit2.HttpException

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun SearchLetter(navigator: DestinationsNavigator) {
    var recipes by rememberSaveable { mutableStateOf<List<Meal>>(emptyList()) }
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var isError by rememberSaveable { mutableStateOf(false) }
    var initialApiCalled by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var query by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            Row (horizontalArrangement = Arrangement.Start){
                Button(onClick = { navigator.navigate(RecipeScreenDestination) }) {
                    Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Back to Home")
                }
                Spacer(modifier = Modifier.width(16.dp))
                com.example.recipeapp.presentation.components.searchbar.SearchBar(
                    onSearchQueryChanged = {
                        query = it
                        coroutineScope.launch {
                            if (!initialApiCalled) {
                                try {
                                    val response = RetrofitInstance.api.getMealByFirstLetter(query)
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
                    }
                )
            }
        }
    ) {
        println("lazy : $recipes")
        LazyRow{
            items(
                items = recipes

            ){recipe->
                ThumbNail(recipe = recipe)
            }
        }
    }
}
