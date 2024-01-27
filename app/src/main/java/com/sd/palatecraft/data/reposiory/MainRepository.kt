package com.sd.palatecraft.data.reposiory

import com.sd.palatecraft.data.local.MealDatabase
import com.sd.palatecraft.data.local.MealEntitiy
import com.sd.palatecraft.data.remote.dto.FilterByArea
import com.sd.palatecraft.data.remote.dto.FilterByCategory
import com.sd.palatecraft.data.remote.dto.ListMealByFirstLetter
import com.sd.palatecraft.data.remote.dto.ListOfArea
import com.sd.palatecraft.data.remote.dto.ListOfCategories
import com.sd.palatecraft.data.remote.dto.ListOfIngredients
import com.sd.palatecraft.data.remote.dto.RandomMeal
import com.sd.palatecraft.data.remote.api.RecipeApi
import com.sd.palatecraft.data.remote.dto.FilterByIngredient
import com.sd.palatecraft.data.remote.dto.SearchMealById
import com.sd.palatecraft.data.remote.dto.SearchMealByName
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class MainRepository(
    private val api: RecipeApi,
    private val database: MealDatabase
): Repository {
    private val dao = database.mealDao()
    override suspend fun getRandomRecipe(): Response<RandomMeal> {
        return api.getRandomRecipe()
    }

    override suspend fun searchById(id: String): Response<SearchMealById> {
        return api.getById(id = id)
    }

    override suspend fun searchByLetter(letter: String): Response<ListMealByFirstLetter> {
        return api.getMealByFirstLetter(firstLetter = letter)
    }

    override suspend fun searchByName(name: String): Response<SearchMealByName>{
        return api.getMealByName(name = name)
    }

    override suspend fun getCategories(): Response<ListOfCategories>{
        return api.listCategories()
    }

    override suspend fun getAreas(): Response<ListOfArea>{
        return api.listAreas()
    }

    override suspend fun getIngredients(): Response<ListOfIngredients>{
        return api.listIngredients()
    }

    override suspend fun filterByCategory(category: String): Response<FilterByCategory>{
        return api.filterByCategory(category = category)
    }

    override suspend fun filterByArea(area: String): Response<FilterByArea> {
        return api.filterByArea(area = area)
    }

    override suspend fun filterByMainIngredient(ingredient: String): Response<FilterByIngredient> {
        return api.filterByIngredient(ingredient = ingredient)
    }

    override suspend fun getMeals(): Flow<List<MealEntitiy>> = dao.getTodos()

    override suspend fun addMeals(meal: MealEntitiy) = dao.addTodo(meal)

    override suspend fun deleteMeals(meal: MealEntitiy) = dao.deleteTodo(meal)
}

