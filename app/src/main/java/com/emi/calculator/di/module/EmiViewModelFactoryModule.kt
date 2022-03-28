package com.emi.calculator.di.module

import com.emi.calculator.repository.EmiDataStoreRepositoryImpl
import com.emi.calculator.repository.EmiRepository
import com.emi.calculator.viewmodel.EmiViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EmiViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(
        emiRepository: EmiRepository,
        dataStoreRepository: EmiDataStoreRepositoryImpl
    ): EmiViewModelFactory =
        EmiViewModelFactory(emiRepository, dataStoreRepository)
}