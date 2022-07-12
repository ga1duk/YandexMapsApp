package com.company.yandexmapstest.di

import com.company.yandexmapstest.repository.MarkerRepository
import com.company.yandexmapstest.repository.MarkerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface MarkerRepositoryModule {

    @Binds
    @Singleton
    fun bindsPostRepositoryImpl(postRepositoryImpl: MarkerRepositoryImpl): MarkerRepository
}