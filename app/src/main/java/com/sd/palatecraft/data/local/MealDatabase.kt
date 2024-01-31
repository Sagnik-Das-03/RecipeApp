package com.sd.palatecraft.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MealEntity::class], version = 1, exportSchema = false)
abstract class MealDatabase: RoomDatabase(){
    abstract fun mealDao(): MealDao
}