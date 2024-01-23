package com.sd.palatecraft.data.reposiory

import com.sd.palatecraft.data.remote.FilterByCategory
import com.sd.palatecraft.data.remote.ListMealByFirstLetter
import com.sd.palatecraft.data.remote.ListOfArea
import com.sd.palatecraft.data.remote.ListOfCategories
import com.sd.palatecraft.data.remote.ListOfIngredients
import com.sd.palatecraft.data.remote.RandomMeal
import com.sd.palatecraft.data.remote.SearchMealById
import com.sd.palatecraft.data.remote.SearchMealByName
import retrofit2.Response

interface Repository {
    suspend fun getRandomRecipe(): Response<RandomMeal>

    suspend fun searchById(id: String): Response<SearchMealById>

    suspend fun searchByLetter(letter: String): Response<ListMealByFirstLetter>

    suspend fun searchByName(name: String): Response<SearchMealByName>

    suspend fun getCategories(): Response<ListOfCategories>

    suspend fun getAreas(): Response<ListOfArea>

    suspend fun getIngredients(): Response<ListOfIngredients>

    suspend fun filterByCategory(category: String): Response<FilterByCategory>
}