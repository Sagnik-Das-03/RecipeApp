package com.sd.palatecraft.presentation.screen

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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sd.palatecraft.presentation.components.listitems.CategoryItem
import com.sd.palatecraft.presentation.screen.destinations.RecipeScreenDestination
import com.sd.palatecraft.remote.Category
import com.sd.palatecraft.response.RetrofitInstance
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private const val TAG = "CategoriesScreen"
@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun CategoriesScreen(navigator: DestinationsNavigator) {
    var categories by rememberSaveable { mutableStateOf<List<Category>>(emptyList()) }
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var isError by rememberSaveable { mutableStateOf(false) }
    var initialApiCalled by rememberSaveable { mutableStateOf(false) }
    val dateTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
    LaunchedEffect(Unit) {
        delay(3000L)
        if (!initialApiCalled) {
            try {
                val response = RetrofitInstance.api.listCategories()
                if (response.isSuccessful) {
                    categories = response.body()?.categories ?: emptyList()
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
                    text = "Loading Categories",
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
    }else {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {
                FilledTonalButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { navigator.navigate(RecipeScreenDestination) }) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Back to Home")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "List of Categories",
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Cursive,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.padding(vertical = 10.dp))
            }
            LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(count = 2)){
                items(
                    items = categories
                ){category->
                    CategoryItem(category = category, navigator = navigator)
                }
            }
        }
    }
}