package com.sd.palatecraft.di

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.sd.palatecraft.MainViewModel
import com.sd.palatecraft.data.local.MealDatabase
import com.sd.palatecraft.data.remote.api.RecipeApi
import com.sd.palatecraft.data.reposiory.MainRepository
import com.sd.palatecraft.data.reposiory.Repository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(module {
                single {
                    Retrofit.Builder()
                        .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(RecipeApi::class.java)
                }
                single {
                    Room
                        .databaseBuilder( this@App, MealDatabase::class.java, "todo.db")
                        .build()
                }
                single<Repository> {
                    MainRepository(database = get(), api = get())
                }

                viewModel {
                    MainViewModel(get())
                }
            })
        }
    }
}