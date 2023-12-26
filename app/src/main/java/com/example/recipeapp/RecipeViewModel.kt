package com.example.recipeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.remote.Meal
import com.example.recipeapp.response.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RecipeViewModel : ViewModel() {
    fun getRandomRecipe(onSuccess: (List<Meal>) -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getRandomRecipe()
                if (response.isSuccessful) {
                    onSuccess(response.body()?.meals ?: emptyList())
                } else {
                    onError()
                }
            } catch (e: HttpException) {
                onError()
            }
        }
    }
}
