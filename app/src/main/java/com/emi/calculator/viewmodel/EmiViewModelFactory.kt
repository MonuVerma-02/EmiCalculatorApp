package com.emi.calculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emi.calculator.repository.EmiDataStoreRepository
import com.emi.calculator.repository.EmiRepository

class EmiViewModelFactory(
    private val emiRepository: EmiRepository,
    private val dataStoreRepository: EmiDataStoreRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EmiViewModel(emiRepository, dataStoreRepository) as T
    }
}