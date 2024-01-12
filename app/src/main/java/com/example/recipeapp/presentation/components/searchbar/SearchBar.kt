package com.example.recipeapp.presentation.components.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(onSearchQueryChanged: (String) -> Unit) {
    var query by remember { mutableStateOf("") }

    OutlinedTextField(
        value = query,
        onValueChange = {
            query = it
            if (query.length > 1) {
                if (query.matches("[0-9]+".toRegex())) {
                    println("User entered a number")
                    return@OutlinedTextField
                } else if (query.matches("[^a-zA-Z0-9 ]".toRegex())) {
                    println("User entered a special character")
                    return@OutlinedTextField
                } else {
                    println("Invalid input")
                }
            } else {
                onSearchQueryChanged.invoke(it)
            }
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

        shape = RoundedCornerShape(50),
        textStyle = MaterialTheme.typography.titleLarge
    )
    Spacer(modifier = Modifier.size(16.dp))
}