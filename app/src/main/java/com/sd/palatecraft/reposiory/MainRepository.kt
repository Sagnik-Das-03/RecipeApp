package com.sd.palatecraft.reposiory

import com.sd.palatecraft.remote.FilterByCategory
import com.sd.palatecraft.remote.ListMealByFirstLetter
import com.sd.palatecraft.remote.ListOfArea
import com.sd.palatecraft.remote.ListOfCategories
import com.sd.palatecraft.remote.ListOfIngredients
import com.sd.palatecraft.remote.RandomMeal
import com.sd.palatecraft.remote.SearchMealByName
import com.sd.palatecraft.response.RetrofitInstance
import retrofit2.Response

class MainRepository {
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

    suspend fun getCategories(): Response<ListOfCategories>{
        return apiService.listCategories()
    }

    suspend fun getAreas(): Response<ListOfArea>{
        return apiService.listAreas()
    }

    suspend fun getIngredients(): Response<ListOfIngredients>{
        return apiService.listIngredients()
    }

    suspend fun filterByCategory(query: String): Response<FilterByCategory>{
        return apiService.filterByCategory(category = query)
    }
}

