package com.emi.calculator.di.module

import com.emi.calculator.repository.EmiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EmiRepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(): EmiRepository = EmiRepository()
}