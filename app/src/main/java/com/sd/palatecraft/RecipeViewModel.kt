package com.sd.palatecraft

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sd.palatecraft.remote.Meal
import com.sd.palatecraft.reposiory.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofLocalizedDateTime
import java.time.format.FormatStyle
private const val TAG = "RecipeViewModel"
@RequiresApi(Build.VERSION_CODES.O)
class RecipeViewModel : ViewModel() {
    private val repository = RecipeRepository() // Assuming you have a repository for API calls

    // MutableStateFlow for recipes
    private val _recipes = MutableStateFlow<List<Meal>>(emptyList())
    val recipes: StateFlow<List<Meal>> = _recipes.asStateFlow()

    // MutableStateFlow for isLoading
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // MutableStateFlow for isError
    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    fun getRandomRecipe(){
        fetchRandomRecipes()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchRandomRecipes() {
        viewModelScope.launch {
            delay(2500L)
            try {
                val response = repository.getRandomRecipe()
                if (response.isSuccessful) {
                    _recipes.value = response.body()?.meals ?: emptyList()
                    Log.i(TAG, "API called: ${getCurrentDateTime()}")
                } else {
                    _isError.value = true
                }
            } catch (e: HttpException) {
                _isError.value = true
                Log.e(TAG, "Exception: ${e.message} \n date: ${getCurrentDateTime()}")
            } catch (e: SocketTimeoutException) {
                _isError.value = true
                Log.e(TAG, "Exception: ${e.message} \n date: ${getCurrentDateTime()}")
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun refreshRandomRecipe(isRefreshing: Boolean){
        refreshRecipes(isRefreshing = isRefreshing)
    }
    private fun refreshRecipes(isRefreshing: Boolean) {
        viewModelScope.launch {
            if (isRefreshing) {
                try {
                    val response = repository.getRandomRecipe()
                    if (response.isSuccessful) {
                        _recipes.value = response.body()?.meals ?: emptyList()
                    } else {
                        _isError.value = true
                    }
                } catch (e: HttpException) {
                    _isError.value = true
                    print(e.printStackTrace())
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDateTime(): String {
        return LocalDateTime.now().format(ofLocalizedDateTime(FormatStyle.MEDIUM))
    }
}
