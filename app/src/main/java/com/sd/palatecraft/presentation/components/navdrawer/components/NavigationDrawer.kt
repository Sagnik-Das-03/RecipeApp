package com.sd.palatecraft.presentation.components.navdrawer.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sd.palatecraft.R
import com.sd.palatecraft.data.remote.dto.Meal
import com.sd.palatecraft.presentation.components.listitems.RecipeItem
import com.sd.palatecraft.presentation.destinations.AreasScreenDestination
import com.sd.palatecraft.presentation.destinations.CategoriesScreenDestination
import com.sd.palatecraft.presentation.destinations.FilterAreaScreenDestination
import com.sd.palatecraft.presentation.destinations.FilterCategoryScreenDestination
import com.sd.palatecraft.presentation.destinations.FilterIngredientScreenDestination
import com.sd.palatecraft.presentation.destinations.IngredientsScreenDestination
import com.sd.palatecraft.presentation.destinations.RecipeScreenDestination
import com.sd.palatecraft.presentation.destinations.SearchLetterDestination
import com.sd.palatecraft.presentation.destinations.SearchNameDestination
import com.sd.palatecraft.presentation.destinations.StarredScreenDestination
import com.sd.palatecraft.ui.theme.BlackA60
import com.sd.palatecraft.util.WithAnimation
import com.sd.palatecraft.util.navItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(navigator: DestinationsNavigator, recipes: List<Meal>) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.verticalScroll(scrollState),
                drawerContainerColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.8f)
                        .background(
                            MaterialTheme.colorScheme.onPrimaryContainer,
                            shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp)
                        )
                ){
                    Image(painter = painterResource(id = R.drawable.icon_bg), contentDescription = "Icon")
                }
                navItems.forEachIndexed { index, navigationItem ->
                    NavigationDrawerItem(
                        shape = RoundedCornerShape(16.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor =MaterialTheme.colorScheme.primaryContainer,
                            selectedTextColor =MaterialTheme.colorScheme.onPrimaryContainer,
                            selectedIconColor =MaterialTheme.colorScheme.onPrimaryContainer,
                            unselectedContainerColor = Color.Transparent,
                            unselectedIconColor =MaterialTheme.colorScheme.primaryContainer,
                            unselectedTextColor =MaterialTheme.colorScheme.primaryContainer,
                        ),
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
                                        navigator.navigate(RecipeScreenDestination)
                                    }
                                    1 -> {
                                        delay(500L)
                                        navigator.navigate(SearchLetterDestination)
                                    }
                                    2 -> {
                                        delay(500L)
                                        navigator.navigate(SearchNameDestination)
                                    }
                                    3 -> {
                                        delay(500L)
                                        navigator.navigate(CategoriesScreenDestination)
                                    }
                                    4 -> {
                                        delay(500L)
                                        navigator.navigate(AreasScreenDestination)
                                    }
                                    5 -> {
                                        delay(500L)
                                        navigator.navigate(IngredientsScreenDestination)
                                    }
                                    6 -> {
                                        delay(500L)
                                        navigator.navigate(FilterCategoryScreenDestination)
                                    }
                                    7 -> {
                                        delay(500L)
                                        navigator.navigate(FilterAreaScreenDestination)
                                    }
                                    8 -> {
                                        delay(500L)
                                        navigator.navigate(FilterIngredientScreenDestination)
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
                            .background(color = Color.Transparent)
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
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        IconButton(
                            modifier = Modifier.background(color = BlackA60, shape = RoundedCornerShape(topStart = 50f, bottomStart = 50f)),
                            onClick = {
                            navigator.navigate(StarredScreenDestination(isError = false))}
                        ) {
                            Icon(imageVector = Icons.Outlined.Star,
                                contentDescription = "Go to Starred",
                                tint = MaterialTheme.colorScheme.onSecondary)
                        }
                    }
                },
                    navigationIcon = {
                        IconButton(
                            modifier = Modifier.background(color = BlackA60, shape = RoundedCornerShape(topEnd = 50f, bottomEnd = 50f)),
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) {
            WithAnimation(animation = fadeIn()+ slideInVertically(), delay = 50) {
                LazyColumn{
                    items(recipes) { recipe ->
                        RecipeItem(recipe)
                    }
                }
            }
        }
    }
}