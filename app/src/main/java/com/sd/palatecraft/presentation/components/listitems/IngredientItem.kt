package com.sd.palatecraft.presentation.components.listitems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
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
import com.sd.palatecraft.data.remote.dto.Ingredients
import com.sd.palatecraft.presentation.destinations.IngredientDetailsScreenDestination

@Composable
fun IngredientItem(ingredient: Ingredients, navigator: DestinationsNavigator) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .background(
                color = MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.onSecondary.copy(0.5f))
                .padding(vertical = 16.dp, horizontal = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = ingredient.strIngredient,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.weight(0.6f)
                )
                FilledTonalIconButton(
                    onClick = { navigator.navigate(IngredientDetailsScreenDestination(ingredientName = ingredient.strIngredient)) },
                    modifier = Modifier.scale(0.8f).weight(0.4f),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.75f),
                        contentColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Icon(imageVector = Icons.Outlined.ArrowForward,
                        contentDescription = "Go to Button",
                        modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = "https://www.themealdb.com/images/ingredients/${ingredient.strIngredient}.png",
                contentDescription = "Ingredient Image")
            Spacer(modifier = Modifier.height(8.dp))
            ingredient?.strDescription?.let {title->
                Text(text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}