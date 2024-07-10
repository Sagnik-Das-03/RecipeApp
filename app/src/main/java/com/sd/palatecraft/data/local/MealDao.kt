package com.sd.palatecraft.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
@Dao
interface MealDao {
    @Upsert
    fun addRecipe(meal: MealEntity)

    @Query("SELECT * FROM `meal`")
    fun getRecipes(): Flow<List<MealEntity>>

    @Update
    fun updateRecipe(todo:MealEntity)

    @Delete
    fun deleteRecipe(todo:MealEntity)
}