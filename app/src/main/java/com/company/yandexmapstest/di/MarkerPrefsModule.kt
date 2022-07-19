package com.company.yandexmapstest.di

import android.content.Context
import com.company.yandexmapstest.util.MarkerPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class MarkerPrefsModule {

    @Provides
    fun providesMarkerPrefsModule(
        @ApplicationContext context: Context
    ): MarkerPreferences = MarkerPreferences(context)
}