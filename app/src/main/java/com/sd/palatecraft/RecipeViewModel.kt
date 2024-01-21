package com.sd.palatecraft

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sd.palatecraft.remote.Meal
import com.sd.palatecraft.reposiory.RecipeRepository
import com.sd.palatecraft.response.RetrofitInstance
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
    private val repository = RecipeRepository()

    private val _recipes = MutableStateFlow<List<Meal>>(emptyList())
    val recipes: StateFlow<List<Meal>> = _recipes.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    private val _currentQuery = MutableStateFlow("")
    val currentQuery: StateFlow<String> = _currentQuery.asStateFlow()

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


    // Function to update the query
    fun updateQuery(query: String) {
        _currentQuery.value = query
    }

    fun searchByLetter(letter: String){
        getByLetter(query = letter)
    }
    private fun getByLetter(query: String){
        viewModelScope.launch {
            delay(500L)
            updateQuery(query = query)
            query.replace(" ", "")
            if (
                query.length == 1
                && !query.matches("[0-9]+".toRegex())
                && !query.matches("[^a-zA-Z0-9 ]".toRegex())
            ) {
                try {
                    val response = RetrofitInstance.api.getMealByFirstLetter(query)
                    if (response.isSuccessful) {
                        _recipes.value = response.body()?.meals ?: emptyList()
                        Log.i(TAG, "Api called: ${getCurrentDateTime()}")
                        _isError.value = false
                        _isLoading.value = false
                    } else {
                        _isError.value = true
                        _message.value = "Error Calling API for query $query : ${getCurrentDateTime()}"
                        Log.e(TAG, _message.value)
                    }
                } catch (e: HttpException) {
                    _isError.value = true
                    _message.value = "Exception:${e.message} \n time: ${getCurrentDateTime()}"
                    Log.e(TAG, _message.value)
                } catch (e: SocketTimeoutException) {
                    _isError.value = true
                    _message.value = "Exception:${e.message} \n time: ${getCurrentDateTime()}"
                    Log.e(TAG, _message.value)
                } finally {
                    _isLoading.value = false
                }

            } else if (
                query.matches("[0-9]+".toRegex()) || query.matches("[^a-zA-Z0-9 ]".toRegex())
            ) {
                _isLoading.value = false
                _isError.value = true
                _message.value = "Search Query: $query is not a Letter"
                Log.e(TAG, "Error Calling API for query $query : ${getCurrentDateTime()}")
            } else if (
                query.isBlank()
            ) {
                _isLoading.value = true
                Log.e(TAG, "Error Calling API for empty query $query : ${getCurrentDateTime()}")
            }else if (
                query.length>1
            ) {
                _isError.value = true
                _isLoading.value = false
                _message.value = "Search Query must be a Single Letter"
                Log.e(TAG, "Error Calling API for empty query $query : ${getCurrentDateTime()}")
            } else {
                _isError.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDateTime(): String {
        return LocalDateTime.now().format(ofLocalizedDateTime(FormatStyle.MEDIUM))
    }
}
