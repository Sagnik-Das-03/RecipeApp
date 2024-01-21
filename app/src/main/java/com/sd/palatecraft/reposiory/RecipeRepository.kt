package com.sd.palatecraft.reposiory

import com.sd.palatecraft.remote.ListMealByFirstLetter
import com.sd.palatecraft.remote.RandomMeal
import com.sd.palatecraft.remote.SearchMealByName
import com.sd.palatecraft.response.RetrofitInstance
import retrofit2.Response

class RecipeRepository {
    private val apiService = RetrofitInstance.api

    suspend fun getRandomRecipe(): Response<RandomMeal> {
        return apiService.getRandomRecipe()
    }

    suspend fun searchByLetter(letter: String): Response<ListMealByFirstLetter> {
        return apiService.getMealByFirstLetter(firstLetter = letter)
    }

    suspend fun searchByName(name: String): Response<SearchMealByName>{
        return apiService.getMealByName(name = name)
    }


}

