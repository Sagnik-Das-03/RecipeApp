package com.sd.palatecraft.presentation.components.navdrawer.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sd.palatecraft.R
import com.sd.palatecraft.presentation.components.listitems.RecipeItem
import com.sd.palatecraft.presentation.screen.destinations.AreasScreenDestination
import com.sd.palatecraft.presentation.screen.destinations.CategoriesScreenDestination
import com.sd.palatecraft.presentation.screen.destinations.FilterCategoryScreenDestination
import com.sd.palatecraft.presentation.screen.destinations.IngredientsScreenDestination
import com.sd.palatecraft.presentation.screen.destinations.SearchLetterDestination
import com.sd.palatecraft.presentation.screen.destinations.SearchNameDestination
import com.sd.palatecraft.remote.Meal
import com.sd.palatecraft.util.navItems
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sd.palatecraft.presentation.screen.destinations.RecipeScreenDestination
import com.sd.palatecraft.ui.theme.Black40
import com.sd.palatecraft.ui.theme.BlackA40
import com.sd.palatecraft.ui.theme.BlackA60
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
            ModalDrawerSheet(
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
                    Row{
                        Text(
                            text = "PalateCraft",
                            letterSpacing = 4.sp,
                            style = MaterialTheme.typography.displaySmall,
                            color = Color.Transparent,
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
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
            LazyColumn{
                items(recipes) { recipe ->
                    RecipeItem(recipe)
                }
            }
        }
    }
}