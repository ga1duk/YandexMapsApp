package com.company.yandexmapstest.di

import android.content.Context
import androidx.room.Room
import com.company.yandexmapstest.R
import com.company.yandexmapstest.dao.MarkerDao
import com.company.yandexmapstest.db.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DB_NAME = "app.db"

@InstallIn(SingletonComponent::class)
@Module
class DbModule {

    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext context: Context
    ): AppDb = Room.databaseBuilder(context, AppDb::class.java, DB_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providePostDao(appDb: AppDb): MarkerDao = appDb.markerDao()
}