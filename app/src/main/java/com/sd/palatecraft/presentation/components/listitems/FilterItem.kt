package com.sd.palatecraft.presentation.components.listitems

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sd.palatecraft.data.remote.dto.FilteredMeals
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterItem(navigator: DestinationsNavigator, filteredMeal: FilteredMeals) {
    var isSheetOpen by remember { mutableStateOf(false) }
    val width = LocalConfiguration.current.screenWidthDp
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .widthIn(min = (width * 0.65).dp, max = (width * 0.85).dp)
            .background(
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 20.dp)
        ) {
            Text(
                text = filteredMeal.strMeal,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            AsyncImage(
                model = filteredMeal.strMealThumb,
                contentDescription = "Thumbnail",
                modifier = Modifier
                    .size((0.55 * width).dp)
                    .clip(RoundedCornerShape(16.dp)))
            FilledTonalButton(
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.75f),
                    contentColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                onClick = { isSheetOpen = true }) {
                Text(text = "Instructions")
                Spacer(modifier = Modifier.height(10.dp))
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Go to",
                    modifier = Modifier.size(18.dp))
            }
            if(isSheetOpen){
                ModalBottomSheet(onDismissRequest = { isSheetOpen = false }) {

                }
            }
        }
    }
}