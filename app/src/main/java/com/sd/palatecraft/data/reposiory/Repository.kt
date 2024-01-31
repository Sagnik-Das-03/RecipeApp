package com.sd.palatecraft.data.reposiory

import com.sd.palatecraft.data.local.MealEntity
import com.sd.palatecraft.data.remote.dto.FilterByArea
import com.sd.palatecraft.data.remote.dto.FilterByCategory
import com.sd.palatecraft.data.remote.dto.FilterByIngredient
import com.sd.palatecraft.data.remote.dto.ListMealByFirstLetter
import com.sd.palatecraft.data.remote.dto.ListOfArea
import com.sd.palatecraft.data.remote.dto.ListOfCategories
import com.sd.palatecraft.data.remote.dto.ListOfIngredients
import com.sd.palatecraft.data.remote.dto.RandomMeal
import com.sd.palatecraft.data.remote.dto.SearchMealById
import com.sd.palatecraft.data.remote.dto.SearchMealByName
import kotlinx.coroutines.flow.Flow
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

    suspend fun filterByArea(area: String): Response<FilterByArea>

    suspend fun filterByMainIngredient(ingredient: String): Response<FilterByIngredient>

    suspend fun getMeals(): Flow<List<MealEntity>>

    suspend fun addMeals(meal:MealEntity)

    suspend fun deleteMeals(meal: MealEntity)
}