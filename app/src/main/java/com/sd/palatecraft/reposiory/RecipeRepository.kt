package com.sd.palatecraft.reposiory

import com.sd.palatecraft.remote.RandomMeal
import com.sd.palatecraft.response.RetrofitInstance
import retrofit2.Response

class RecipeRepository {
    private val apiService = RetrofitInstance.api

    suspend fun getRandomRecipe(): Response<RandomMeal> {
        return apiService.getRandomRecipe()
    }
}

