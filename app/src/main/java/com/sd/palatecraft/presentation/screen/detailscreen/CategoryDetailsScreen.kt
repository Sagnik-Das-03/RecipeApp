package com.sd.palatecraft.presentation.screen.detailscreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sd.palatecraft.presentation.components.IngredientsList
import com.sd.palatecraft.presentation.components.Instructions
import com.sd.palatecraft.presentation.components.videoplayer.YoutubePlayer
import com.sd.palatecraft.presentation.screen.destinations.CategoriesScreenDestination
import com.sd.palatecraft.remote.FilteredMeals
import com.sd.palatecraft.remote.Meal
import com.sd.palatecraft.response.RetrofitInstance
import com.sd.palatecraft.ui.theme.Black40
import com.sd.palatecraft.ui.theme.BlackA40
import com.sd.palatecraft.ui.theme.BlackA60
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private const val TAG = "Category Details Screen"
@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun CategoryDetailsScreen(category: String, navigator: DestinationsNavigator) {
    var filteredMeals by rememberSaveable { mutableStateOf<List<FilteredMeals>>(emptyList()) }
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var isError by rememberSaveable { mutableStateOf(false) }
    var initialApiCalled by rememberSaveable { mutableStateOf(false) }
    val dateTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
    LaunchedEffect(Unit) {
        delay(250L)
        if (!initialApiCalled) {
            try {
                val response = RetrofitInstance.api.filterByCategory(category)
                if (response.isSuccessful) {
                    filteredMeals = response.body()?.meals ?: emptyList()
                    Log.i(TAG, "API called: $dateTime")
                } else {
                    isError = true
                }
            } catch (e: HttpException) {
                isError = true
                Log.e(TAG, "Exception:${e.message} \n date: $dateTime")
            }catch (e: SocketTimeoutException) {
                isError = true
                Log.e(TAG, "Exception:${e.message} \n date: $dateTime")
            } finally {
                isLoading = false
                initialApiCalled = true
            }
        }
    }
    if(isLoading){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Loading Category",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.height(5.dp)
                )
            }
        }
    }else if(isError){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ){
            Text(text = "Error Fetching Data")
        }
    } else {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)) {
                FilledTonalIconButton( modifier = Modifier.weight(0.15f),onClick = { navigator.navigate(CategoriesScreenDestination) }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back To List of Categories")
                }
                Spacer(modifier = Modifier.width(18.dp))
                Text(text = "List of Dishes", color = MaterialTheme.colorScheme.onPrimary, fontFamily = FontFamily.Cursive, style = MaterialTheme.typography.displaySmall, modifier = Modifier.padding(vertical = 8.dp).weight(0.75f))
            }
            LazyHorizontalStaggeredGrid(rows = StaggeredGridCells.Fixed(2)){
                items(
                    items = filteredMeals
                ){meal ->
                    CategoryDetailsItem(meal = meal)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailsItem(meal: FilteredMeals) {
    val width = LocalConfiguration.current.screenWidthDp
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(10.dp)
            .widthIn(min = (width * 0.6).dp, max = (width * 0.9).dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(12.dp)
            )){
        Column(
            modifier = Modifier.padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = meal.strMeal, textAlign = TextAlign.Center, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(10.dp))
            AsyncImage(model = meal.strMealThumb, contentDescription = "Meal Thumbnail",
                modifier = Modifier
                    .scale(0.7f)
                    .clip(shape = RoundedCornerShape(12.dp)))
            FilledTonalButton(colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f),
                contentColor = MaterialTheme.colorScheme.primaryContainer
            ), onClick = { isSheetOpen = true }) {
                Text(text = "Open Instructions")
                Spacer(modifier = Modifier.width(10.dp))
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Open Instructions")
            }
            if(isSheetOpen){
                ModalBottomSheet(onDismissRequest = { isSheetOpen = false }) {
                    val lifecycleOwner = LocalLifecycleOwner.current
                    var meals by rememberSaveable { mutableStateOf<List<Meal>>(emptyList()) }
                    var isLoading by rememberSaveable { mutableStateOf(true) }
                    var isError by rememberSaveable { mutableStateOf(false) }
                    var initialApiCalled by rememberSaveable { mutableStateOf(false) }
                    val dateTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
                    LaunchedEffect(Unit){
                        try {
                            val response = RetrofitInstance.api.getMealByName(meal.strMeal)
                            if (response.isSuccessful) {
                                meals = response.body()?.meals ?: emptyList()
                                Log.i(TAG, "API called: $dateTime")
                            } else {
                                isError = true
                            }
                        } catch (e: HttpException) {
                            isError = true
                            Log.e(TAG, "Exception:${e.message} \n date: $dateTime")
                        }catch (e: SocketTimeoutException) {
                            isError = true
                            Log.e(TAG, "Exception:${e.message} \n date: $dateTime")
                        } finally {
                            isLoading = false
                            initialApiCalled = true
                        }
                    }
                    LazyColumn{
                        items(
                            items = meals
                        ){ selectedMeal ->
                            Text(
                                color = MaterialTheme.colorScheme.outline,
                                text = selectedMeal.strMeal,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth())
                            Spacer(modifier = Modifier.height(8.dp))
                            YoutubePlayer(
                                youtubeVideoId = selectedMeal.strYoutube.substringAfter("="),
                                lifecycleOwner = lifecycleOwner)
                            Instructions(recipe = selectedMeal)
                            IngredientsList(recipe = selectedMeal)
                        }
                    }
                }
            }
        }
    }
}