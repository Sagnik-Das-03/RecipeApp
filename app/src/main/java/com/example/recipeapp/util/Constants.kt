package com.example.recipeapp.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import com.example.recipeapp.R
import com.example.recipeapp.presentation.components.navdrawer.components.NavigationItem

val backgrounds = mutableListOf<Int>(
    R.drawable.food1, R.drawable.food2, R.drawable.food3,
    R.drawable.food4,
    R.drawable.food5,
    R.drawable.food6,
    R.drawable.food7, R.drawable.food8, R.drawable.food9,
    R.drawable.food10, R.drawable.food11, R.drawable.food12)


val navItems = listOf(
    NavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    NavigationItem(
        title = "Search by Letter",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
    ),
    NavigationItem(
        title = "Search by Name",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
    ),
    NavigationItem(
        title = "All Meal Categories",
        selectedIcon = Icons.Filled.List,
        unselectedIcon = Icons.Outlined.List,
    ),
    NavigationItem(
        title = "List of Areas",
        selectedIcon = Icons.Filled.List,
        unselectedIcon = Icons.Outlined.List,
    ),
    NavigationItem(
        title = "List of Ingredients",
        selectedIcon = Icons.Filled.List,
        unselectedIcon = Icons.Outlined.List,
    )
)