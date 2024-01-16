package com.example.recipeapp.presentation.components.navdrawer.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.R
import com.example.recipeapp.presentation.screen.RecipeItem
import com.example.recipeapp.presentation.screen.destinations.CategoriesScreenDestination
import com.example.recipeapp.presentation.screen.destinations.RecipeScreenDestination
import com.example.recipeapp.presentation.screen.destinations.SearchLetterDestination
import com.example.recipeapp.presentation.screen.destinations.SearchNameDestination
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
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.8f)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp)
                        )
                ){
                    Image(painter = painterResource(id = R.drawable.icon_bg), contentDescription = "Icon")
                }
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
                                        delay(1000L)
                                        drawerState.close()
                                    }
                                    1 -> {
                                        delay(1000L)
                                        navigator.navigate(SearchLetterDestination)
                                    }
                                    2 -> {
                                        delay(1000L)
                                        navigator.navigate(SearchNameDestination)
                                    }
                                    3 -> {
                                        delay(1000L)
                                        navigator.navigate(CategoriesScreenDestination)
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
                    Row {
                        Text(
                            text = "PalateCraft",
                            letterSpacing = 3.sp,
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
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
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
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