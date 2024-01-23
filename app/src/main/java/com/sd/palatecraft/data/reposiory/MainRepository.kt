package com.sd.palatecraft.data.reposiory

import com.sd.palatecraft.data.remote.FilterByCategory
import com.sd.palatecraft.data.remote.ListMealByFirstLetter
import com.sd.palatecraft.data.remote.ListOfArea
import com.sd.palatecraft.data.remote.ListOfCategories
import com.sd.palatecraft.data.remote.ListOfIngredients
import com.sd.palatecraft.data.remote.RandomMeal
import com.sd.palatecraft.data.remote.RecipeApi
import com.sd.palatecraft.data.remote.SearchMealById
import com.sd.palatecraft.data.remote.SearchMealByName
import retrofit2.Response

class MainRepository(
    private val api: RecipeApi
): Repository {

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

}

