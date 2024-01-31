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
    fun addTodo(meal: MealEntity)

    @Query("SELECT * FROM `meal`")
    fun getTodos(): Flow<List<MealEntity>>

    @Update
    fun updateTodo(todo:MealEntity)

    @Delete
    fun deleteTodo(todo:MealEntity)
}