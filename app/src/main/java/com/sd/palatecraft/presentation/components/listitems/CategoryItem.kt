package com.sd.palatecraft.presentation.components.listitems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sd.palatecraft.presentation.destinations.CategoryDetailsScreenDestination
import com.sd.palatecraft.data.remote.dto.Category

@Composable
fun CategoryItem(category: Category, navigator: DestinationsNavigator) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 10.dp)
            .background(
                color = MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f))
                .padding(vertical = 20.dp, horizontal = 10.dp)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ){
                Text(
                    text = category.strCategory,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(0.5f))
                IconButton(
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.75f),
                        contentColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    modifier = Modifier.scale(0.8f).weight(0.5f), onClick = { navigator.navigate(
                        CategoryDetailsScreenDestination(category = category.strCategory)
                    )}) {
                    Icon(imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Go to Category")
                }
            }
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