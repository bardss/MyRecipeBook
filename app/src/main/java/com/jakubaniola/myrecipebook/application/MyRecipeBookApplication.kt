package com.jakubaniola.myrecipebook.application

import android.app.Application
import android.content.Context
import com.jakubaniola.myrecipebook.di.databaseModule
import com.jakubaniola.myrecipebook.di.repositoryModule
import com.jakubaniola.myrecipebook.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyRecipeBookApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    context = applicationContext
    initKoin()
  }

  private fun initKoin() {
    startKoin {
      androidContext(this@MyRecipeBookApplication)
      modules(
        listOf(
          databaseModule,
          repositoryModule,
          viewModelModule
        )
      )
    }
  }

  companion object {
    lateinit var context: Context
  }
}