package com.sd.palatecraft.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sd.palatecraft.data.remote.dto.Meal
import com.sd.palatecraft.ui.theme.WhiteA40

@Composable
fun Instructions(recipe: Meal) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = WhiteA40)
    ) {
        Column (modifier = Modifier
            .background(
                brush = Brush.linearGradient(colors = listOf(MaterialTheme.colorScheme.inverseOnSurface, MaterialTheme.colorScheme.surface)),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(PaddingValues(35.dp, 30.dp))
        ){
            Text(text = "Instructions",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 10.dp))
            Divider(thickness = 3.dp, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(10.dp, 15.dp))
            Text(
                text = recipe.strInstructions,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 10.dp))
        }
    }
}