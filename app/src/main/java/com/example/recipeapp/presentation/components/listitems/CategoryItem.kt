package com.example.recipeapp.presentation.components.listitems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipeapp.remote.Category

@Composable
fun CategoryItem(category: Category) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 10.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp)
        ) {
            Text(
                text = category.strCategory,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = "https://www.themealdb.com/images/category/${category.strCategory}.png",
                contentDescription = "Category Image")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.strCategoryDescription,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.labelLarge)
        }
    }
}