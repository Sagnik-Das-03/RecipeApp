package com.example.recipeapp.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi  {
    //www.themealdb.com/api/json/v1/1/random.php
    @GET("random.php")
    suspend fun getRandomRecipe() : Response<RandomMeal>

    // www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata
    @GET("search.php")
    suspend fun getMealByName(@Query("s") name: String): Response<SearchMealByName>

    //https://www.themealdb.com/api/json/v1/1/categories.php
    @GET("categories.php")
    suspend fun listCategories(): Response<ListOfCategories>

    // www.themealdb.com/api/json/v1/1/search.php?f=a
    @GET("search.php")
    suspend fun getMealByFirstLetter(@Query("f") firstLetter: String): Response<ListMealByFirstLetter>

    //https://www.themealdb.com/api/json/v1/1/list.php?a=list
    @GET("/list.php")
    suspend fun listAreas(@Query("a") areas: String = "list"): Response<ListOfArea>

    // www.themealdb.com/api/json/v1/1/list.php?i=list
    @GET("/list.php")
    suspend fun listIngredients(@Query("i") ingredients: String = "list"): Response<ListOfIngredients>

    //https://www.themealdb.com/api/json/v1/1/filter.php?i=chicken_breast
    @GET("/filter.php")
    suspend fun filterByIngredient(@Query("i") ingredient: String): Response<FilterByIngredient>

    //https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    @GET("/filter.php")
    suspend fun filterByCategory(@Query("i") category: String): Response<FilterByCategory>

    //www.themealdb.com/api/json/v1/1/filter.php?a=Canadian
    @GET("/filter.php")
    suspend fun filterByArea(@Query("a") area: String): Response<FilterByArea>
}