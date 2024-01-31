package com.sd.palatecraft.presentation.components.listitems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sd.palatecraft.data.remote.dto.Area
import com.sd.palatecraft.presentation.destinations.AreaDetailsScreenDestination

@Composable
fun AreasItem(area: Area, navigator: DestinationsNavigator) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                shape = RoundedCornerShape(10.dp)
            )
    ){
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.background(color = MaterialTheme.colorScheme.onSecondary.copy(0.5f))) {
            Text(
                text = area.strArea,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .padding(vertical = 32.dp, horizontal = 16.dp)
                    .weight(0.6f))
            FilledTonalIconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.75f),
                    contentColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.4f),
                onClick = { navigator.navigate(AreaDetailsScreenDestination(areaName = area.strArea)) }) {
                Icon(
                    imageVector = Icons.Outlined.ArrowForward,
                    contentDescription = "Go to Cuisine",
                    modifier = Modifier.size(18.dp))
            }
        }
    }
}