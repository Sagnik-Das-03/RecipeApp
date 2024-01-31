package com.sd.palatecraft

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.palatecraft.data.local.MealEntity
import com.sd.palatecraft.data.remote.dto.Area
import com.sd.palatecraft.data.remote.dto.Category
import com.sd.palatecraft.data.remote.dto.FilteredMeals
import com.sd.palatecraft.data.remote.dto.Ingredients
import com.sd.palatecraft.data.remote.dto.Meal
import com.sd.palatecraft.data.reposiory.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofLocalizedDateTime
import java.time.format.FormatStyle
private const val TAG = "RecipeViewModel"
@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Meal>>(emptyList())
    val recipes: StateFlow<List<Meal>> = _recipes.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _areas = MutableStateFlow<List<Area>>(emptyList())
    val areas: StateFlow<List<Area>> = _areas.asStateFlow()

    private val _ingredients = MutableStateFlow<List<Ingredients>>(emptyList())
    val ingredients: StateFlow<List<Ingredients>> = _ingredients.asStateFlow()

    private val _filteredMeals = MutableStateFlow<List<FilteredMeals>>(emptyList())
    val filteredMeals: StateFlow<List<FilteredMeals>> = _filteredMeals.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    private val _currentQuery = MutableStateFlow("")
    val currentQuery: StateFlow<String> = _currentQuery.asStateFlow()

    private val _meals:MutableStateFlow<List<MealEntity>> = MutableStateFlow(emptyList())
    val meals = _meals.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDateTime(): String {
        return LocalDateTime.now().format(ofLocalizedDateTime(FormatStyle.MEDIUM))
    }

    fun getRandomRecipe(){
        fetchRandomRecipes()
    }
    private var isDataLoaded = false
    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchRandomRecipes() {
        viewModelScope.launch {
            delay(1000L)
            if(!isDataLoaded){
                try {
                    val response = repository.getRandomRecipe()
                    if (response.isSuccessful) {
                        _recipes.value = response.body()?.meals ?: emptyList()
                        Log.i(TAG, "API called: ${getCurrentDateTime()}")
                    } else {
                        _isError.value = true
                        _message.value = "Check Internet Connection"
                    }
                }catch (e: HttpException) {
                    _isError.value = true
                    Log.e(TAG, "Exception: ${e.message} \n date: ${getCurrentDateTime()}")
                } catch (e: SocketTimeoutException) {
                    _isError.value = true
                    Log.e(TAG, "Exception: ${e.message} \n date: ${getCurrentDateTime()}")
                }catch (e: Exception) {
                    _isError.value = true
                    _message.value = "${e.message}"
                    Log.e(TAG, "Exception: ${e.message} \n date: ${getCurrentDateTime()}")
                } finally {
                    isDataLoaded = true
                    _isLoading.value = false
                }
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
                }catch (e: HttpException) {
                    _isError.value = true
                    Log.e(TAG, "Exception: ${e.message} \n date: ${getCurrentDateTime()}")
                } catch (e: SocketTimeoutException) {
                    _isError.value = true
                    Log.e(TAG, "Exception: ${e.message} \n date: ${getCurrentDateTime()}")
                }catch (e: Exception) {
                    _isError.value = true
                    _message.value = "${e.message}"
                    Log.e(TAG, "Exception: ${e.message} \n date: ${getCurrentDateTime()}")
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun updateQuery(query: String) {
        _currentQuery.value = query
    }
    fun searchByLetter(letter: String){
        getByLetter(query = letter)
    }

    private fun getByLetter(query: String){
        viewModelScope.launch {
            updateQuery(query = query)
            delay(250L)
            query.replace(" ", "")
            if (
                query.length == 1
                && !query.matches("[0-9]+".toRegex())
                && !query.matches("[^a-zA-Z0-9 ]".toRegex())
            ) {
                try {
                    val response = repository.searchByLetter(letter = query)
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


    fun searchByName(name: String){
        getByName(query = name)
    }
    private fun getByName(query: String){
        viewModelScope.launch {
            updateQuery(query = query)
            delay(2500L)
            if (
                query.length > 1
            ) {
                try {
                    val response = repository.searchByName(name = query)
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
                query.isBlank()
            ) {
                _isLoading.value = true
                Log.e(TAG, "Error Calling API for empty blank query $query : ${getCurrentDateTime()}")
            } else {
                _isError.value = false
            }
        }
    }

    fun listCategories(){
        getCategories()
    }
    private fun getCategories(){
        viewModelScope.launch {
            delay(250L)
            try {
                val response = repository.getCategories()
                if (response.isSuccessful) {
                    _categories.value = response.body()?.categories ?: emptyList()
                    Log.i(TAG, "API called: ${getCurrentDateTime()}")
                } else {
                    _isError.value = true
                }
            } catch (e: HttpException) {
                _isError.value= true
                Log.e(TAG, "Exception:${e.message} \n date: ${getCurrentDateTime()}")
            }catch (e: SocketTimeoutException) {
                _isError.value = true
                Log.e(TAG, "Exception:${e.message} \n date: ${getCurrentDateTime()}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun listAreas(){
        getAreas()
    }
    private fun getAreas(){
        viewModelScope.launch {
            delay(250L)
            try {
                val response = repository.getAreas()
                if (response.isSuccessful) {
                    Log.i(TAG, "${response.body()}")
                    _areas.value = response.body()?.meals ?: emptyList()
                    Log.i(TAG, "$areas")
                    Log.i(TAG, "API called: ${getCurrentDateTime()}")
                } else {
                    _isError.value = true
                }
            } catch (e: HttpException) {
                _isError.value = true
                Log.e(TAG, "Exception:${e.message} \n date: ${getCurrentDateTime()}")
            }catch (e: SocketTimeoutException) {
                _isError.value = true
                Log.e(TAG, "Exception:${e.message} \n date: ${getCurrentDateTime()}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun listIngredients() {
        getIngredients()
    }

    private fun getIngredients(){
        viewModelScope.launch {
            try {
                val response = repository.getIngredients()
                if (response.isSuccessful) {
                    _ingredients.value = response.body()?.meals ?: emptyList()
                    Log.i(TAG, "API called: ${getCurrentDateTime()}")
                } else {
                    _isError.value = true
                }
            } catch (e: HttpException) {
                _isError.value = true
                Log.e(TAG, "Exception:${e.message} \n date: ${getCurrentDateTime()}")
            }catch (e: SocketTimeoutException) {
                _isError.value = true
                Log.e(TAG, "Exception:${e.message} \n date: ${getCurrentDateTime()}")
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun searchByCategory(category: String){
        getByCategory(query = category)
    }
    private fun getByCategory(query: String) {
        viewModelScope.launch {
            updateQuery(query = query)
            delay(250L)
            if (
                query.length > 1
                && !query.contains(regex = "[0-9]+".toRegex())
                && !query.contains(regex = "[^a-zA-Z0-9 ]".toRegex())
            ) {
                try {
                    val response =repository.filterByCategory(category = query)
                    if (response.isSuccessful) {
                        _filteredMeals.value = response.body()?.meals ?: emptyList()
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
                query.isBlank()
            ) {
                _isLoading.value = true
                Log.e(TAG, "Error Calling API for empty blank query $query : ${getCurrentDateTime()}")
            }else if (
                query.contains(regex = "[0-9]+".toRegex()) || query.contains(regex = "[^a-zA-Z0-9 ]".toRegex())
            ) {
                _isError.value = true
                _isLoading.value = false
                _message.value = "Search Query: $query is not a Word"
                Log.e(TAG, "Error Calling API for query $query : ${getCurrentDateTime()}")
            } else {
                _isError.value = false
            }
        }
    }


    fun searchById(id: String){
        getById(id = id)
    }
    private fun getById(id: String){
        viewModelScope.launch {
            delay(1500L)
            try {
                val response = repository.searchById(id)
                if (response.isSuccessful) {
                    _recipes.value = response.body()?.meals ?: emptyList()
                    Log.i(TAG, "API called: ${getCurrentDateTime()}")
                } else {
                    _isError.value = true
                    _message.value = "Check Internet Connection"
                }
            }catch (e: HttpException) {
                _isError.value = true
                Log.e(TAG, "Exception: ${e.message} \n date: ${getCurrentDateTime()}")
            } catch (e: SocketTimeoutException) {
                _isError.value = true
                Log.e(TAG, "Exception: ${e.message} \n date: ${getCurrentDateTime()}")
            }catch (e: Exception) {
                _isError.value = true
                _message.value = "${e.message}"
                Log.e(TAG, "Exception: ${e.message} \n date: ${getCurrentDateTime()}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchByArea(area: String){
        getByArea(query = area)
    }
    private fun getByArea(query: String) {
        viewModelScope.launch {
            updateQuery(query = query)
            delay(2500L)
            if (
                query.length > 1
                && !query.contains(regex = "[0-9]+".toRegex())
                && !query.contains(regex = "[^a-zA-Z0-9 ]".toRegex())
            ) {
                try {
                    val response =repository.filterByArea(area = query)
                    if (response.isSuccessful) {
                        _filteredMeals.value = response.body()?.meals ?: emptyList()
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
                query.isBlank()
            ) {
                _isLoading.value = true
                Log.e(TAG, "Error Calling API for empty blank query $query : ${getCurrentDateTime()}")
            }else if (
                query.contains(regex = "[0-9]+".toRegex()) || query.contains(regex = "[^a-zA-Z0-9 ]".toRegex())
            ) {
                _isError.value = true
                _isLoading.value = false
                _message.value = "Search Query: $query is not a Word"
                Log.e(TAG, "Error Calling API for query $query : ${getCurrentDateTime()}")
            } else {
                _isError.value = false
            }
        }
    }


    fun searchByIngredient(ingredient: String){
        getByIngredient(query = ingredient)
    }
    private fun getByIngredient(query: String){
        viewModelScope.launch {
            updateQuery(query = query)
            delay(2500L)
            if (
                query.length > 1
                && !query.contains(regex = "[0-9]+".toRegex())
                && !query.contains(regex = "[^a-zA-Z0-9 ]".toRegex())
            ) {
                try {
                    val response =repository.filterByMainIngredient(ingredient = query)
                    if (response.isSuccessful) {
                        _filteredMeals.value = response.body()?.meals ?: emptyList()
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
                query.isBlank()
            ) {
                _isLoading.value = true
                Log.e(TAG, "Error Calling API for empty blank query $query : ${getCurrentDateTime()}")
            }else if (
                query.contains(regex = "[0-9]+".toRegex()) || query.contains(regex = "[^a-zA-Z0-9 ]".toRegex())
            ) {
                _isError.value = true
                _isLoading.value = false
                _message.value = "Search Query: $query is not a Word"
                Log.e(TAG, "Error Calling API for query $query : ${getCurrentDateTime()}")
            } else {
                _isError.value = false
            }
        }
    }

    init {
        getMeals()
    }
    private fun getMeals(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMeals().collect{data->
                _meals.update { data }
            }
        }
    }

    fun addMeal(meal: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMeals(meal)
        }
    }
    fun deleteMeal(meal: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMeals(meal)
        }
    }
}
