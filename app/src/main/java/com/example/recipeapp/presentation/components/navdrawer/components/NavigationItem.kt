package com.example.recipeapp.presentation.components.navdrawer.components

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = 0,
    val contentDescription: String? = title
) {

}
