@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipeapp.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.presentation.components.ThumbNail
import com.example.recipeapp.remote.Meal
import com.example.recipeapp.response.RetrofitInstance
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SearchRecipeLetterScreen(navigator: DestinationsNavigator) {
    var recipes by rememberSaveable { mutableStateOf<List<Meal>>(emptyList()) }
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var isError by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var searchJob: Job? = null
    val dateTime = remember {
        LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
    }
    var queryOuter  = remember{("") }
    Scaffold(
        topBar = {
            SearchBar(
                onSearchQueryChanged = { query ->
                    queryOuter = query
                    coroutineScope.launch {
                        // Cancel the previous job if it exists
                        searchJob?.cancel()
                        // Start a new coroutine with delay
                        searchJob = launch {
                            isLoading = true
                            delay(2000) // Adjust the delay duration as needed
                            if (query.isNotBlank()
                                && query.length == 1
                                && !(query.matches("[0-9]+".toRegex()))
                                && !(query.matches("[^a-zA-Z0-9 ]".toRegex()))
                                ) {
                                try {
                                    val response = RetrofitInstance.api.getMealByFirstLetter(query)
                                    if (response.isSuccessful) {
                                        recipes = response.body()?.meals ?: emptyList()
                                        Log.d("API", "API called at : $dateTime")
                                        Log.d("MealList", "Number of recipes: ${recipes.size}")
                                    } else {
                                        isError = true
                                        Log.e("MealList", "API call unsuccessful at : $dateTime")
                                    }
                                } catch (e: HttpException) {
                                    isError = true
                                    Log.e(
                                        "MealList",
                                        "API call failed with exception: ${e.message}"
                                    )
                                } finally {
                                    isLoading = false
                                }
                            }else {
                                Log.e("API", "API not Called at :$dateTime")
                                // If query is empty, trigger a refresh
                                recipes = emptyList()
                                isLoading = false
                            }
                        }
                    }
                }
            )
        }
    ) {
        if (isLoading || queryOuter.isBlank()) {
            // Show progress indicator
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Loading Recipes",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 30.dp))
                    LinearProgressIndicator(color = MaterialTheme.colorScheme.primary, modifier = Modifier.height(5.dp))
                }
            }
        } else if (isError) {
            ErrorDialog()
        }
        else {
            Box(modifier = Modifier
                .padding(start = 15.dp, top = 80.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(50)
                )
            ){
                Text(
                    text = "Search Results : ${recipes.size}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(15.dp, 5.dp))
            }
            // Show LazyRow when not loading
            LazyColumn(contentPadding = PaddingValues(top = 120.dp)) {
                items(items = recipes) { meal ->
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .size(10.dp)
                        .background(
                            MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                        ))
                    RecipeItem(recipe = meal)
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .size(10.dp)
                        .background(
                            MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                        ))
                    Spacer(modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth(1f)
                        .height(10.dp))
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onSearchQueryChanged: (String) -> Unit) {
    var query by remember { mutableStateOf("") }

    OutlinedTextField(
        value = query,
        onValueChange = {
            query = it
            onSearchQueryChanged.invoke(it)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search,
                contentDescription = "Search Button",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 20.dp))
        },
        placeholder = {
            Row (modifier = Modifier.padding(0.dp, 3.dp, 10.dp, 3.dp)){
                Text("Search for meals by first letter" , color = MaterialTheme.colorScheme.primary)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.inversePrimary,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                ),
                shape = RoundedCornerShape(50),
                alpha = 0.7f
            )
            .border(
                color = MaterialTheme.colorScheme.onSecondary,
                width = 2.dp,
                shape = RoundedCornerShape(50)
            ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {

            }
        ),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colorScheme.secondary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
            focusedBorderColor = MaterialTheme.colorScheme.onSecondary
        ),
        shape = RoundedCornerShape(50),
        textStyle = MaterialTheme.typography.titleLarge
    )
    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
fun ErrorDialog() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Enter Valid Input", fontSize = 60.sp, color = Color.Red)
    }
}
