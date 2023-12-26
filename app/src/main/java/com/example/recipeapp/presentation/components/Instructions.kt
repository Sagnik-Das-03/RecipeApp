package com.example.recipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.remote.Meal
import com.example.recipeapp.ui.theme.Black40
import com.example.recipeapp.ui.theme.BlackA40
import com.example.recipeapp.ui.theme.BlackA60
import com.example.recipeapp.ui.theme.WhiteA40

@Composable
fun Instructions(recipe: Meal) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = WhiteA40)
    ) {
        Column (modifier = Modifier
            .background(
                brush = Brush.linearGradient(colors = listOf(BlackA40, WhiteA40)),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(PaddingValues(25.dp, 30.dp))
        ){
            Text(text = "Instructions",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                color = BlackA60)
            Divider(thickness = 3.dp, color = BlackA60, modifier = Modifier.padding(0.dp, 15.dp))
            Text(
                text = recipe.strInstructions,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                color = BlackA60
            )
        }
    }
}