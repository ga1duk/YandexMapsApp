package com.company.yandexmapstest.di

import android.content.Context
import com.company.yandexmapstest.util.CoordinatesPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class CoordinatesPrefsModule {

    @Provides
    fun providesCoordinatesPrefsModule(
        @ApplicationContext context: Context
    ): CoordinatesPreferences = CoordinatesPreferences(context)
}