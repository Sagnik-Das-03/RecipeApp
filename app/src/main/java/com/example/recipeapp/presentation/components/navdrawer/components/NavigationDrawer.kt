package com.example.recipeapp.presentation.components.navdrawer.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recipeapp.presentation.screen.RecipeItem
import com.example.recipeapp.presentation.screen.destinations.RecipeScreenDestination
import com.example.recipeapp.presentation.screen.destinations.SearchLetterDestination
import com.example.recipeapp.remote.Meal
import com.example.recipeapp.util.navItems
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(navigator: DestinationsNavigator, recipes: List<Meal>) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Box(modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.8f)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
                )
                navItems.forEachIndexed { index, navigationItem ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = navigationItem.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            scope.launch {
                                when(selectedItemIndex){
                                    0 -> {
                                        delay(500L)
                                        drawerState.close()
                                    }
                                    1 -> {
                                        delay(500L)
                                        navigator.navigate(SearchLetterDestination)
                                    }
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    navigationItem.selectedIcon
                                } else navigationItem.unselectedIcon,
                                contentDescription = navigationItem.title
                            )
                        },
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
            }
        },
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(
                        text = "Recipe App",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        fontWeight = FontWeight.ExtraBold)
                },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu, 
                                contentDescription = "Menu", 
                                tint = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                )
            }
        ) {
            LazyColumn (modifier = Modifier.padding(5.dp, 30.dp, 5.dp, 0.dp)){
                items(recipes) { recipe ->
                    RecipeItem(recipe)
                }
            }
        }
    }
}