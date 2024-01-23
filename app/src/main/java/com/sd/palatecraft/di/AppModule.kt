package com.sd.palatecraft.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.sd.palatecraft.MainViewModel
import com.sd.palatecraft.data.remote.RecipeApi
import com.sd.palatecraft.data.reposiory.MainRepository
import com.sd.palatecraft.data.reposiory.Repository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RequiresApi(Build.VERSION_CODES.O)
val appModule = module {
   single {
       Retrofit.Builder()
           .baseUrl("https://www.themealdb.com/api/json/v1/1/")
           .addConverterFactory(GsonConverterFactory.create())
           .build()
           .create(RecipeApi::class.java)
   }
    single<Repository> {
        MainRepository(get())
    }

    viewModel {
        MainViewModel(get())
    }
}

