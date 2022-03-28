package com.emi.calculator.di.module

import android.content.Context
import com.emi.calculator.repository.EmiDataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EmiDataStoreRepositoryModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) = EmiDataStoreRepository(context)

}