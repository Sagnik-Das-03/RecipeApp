package com.sd.palatecraft.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
@Dao
interface MealDao {
    @Upsert
    fun addTodo(meal: MealEntitiy)

    @Query("SELECT * FROM `meal`")
    fun getTodos(): Flow<List<MealEntitiy>>

    @Update
    fun updateTodo(todo:MealEntitiy)

    @Delete
    fun deleteTodo(todo:MealEntitiy)
}