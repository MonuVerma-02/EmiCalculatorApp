package com.emi.calculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emi.calculator.data.Emi
import com.emi.calculator.data.EmiPreferences
import com.emi.calculator.extension.empty
import com.emi.calculator.repository.EmiDataStoreRepositoryImpl
import com.emi.calculator.repository.EmiRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class EmiViewModel @Inject constructor(
    private val emiRepository: EmiRepository,
    private val dataStoreRepository: EmiDataStoreRepositoryImpl
) : ViewModel() {

    private val _amount = MutableStateFlow(String.empty())
    val amount: StateFlow<String> = _amount
    fun onLoanAmountTextChanged(text: CharSequence) = viewModelScope.launch {
        _amount.emit(text.toString())
    }

    private val _rate = MutableStateFlow(String.empty())
    val rate: StateFlow<String> = _rate
    fun onRateTextChanged(text: CharSequence) = viewModelScope.launch {
        _rate.emit(text.toString())
    }

    private val _tenureYear = MutableStateFlow(String.empty())
    val tenureYear: StateFlow<String> = _tenureYear
    fun onYearTextChanged(text: CharSequence) = viewModelScope.launch {
        _tenureYear.emit(text.toString())
    }

    private val _tenureMonth = MutableStateFlow(String.empty())
    val tenureMonth: StateFlow<String> = _tenureMonth
    fun onMonthTextChanged(text: CharSequence) = viewModelScope.launch {
        _tenureMonth.emit(text.toString())
    }

    val isFormValid: Flow<Boolean> = combine(
        amount,
        rate,
        tenureYear,
        tenureMonth
    ) { amount, rate, tenureYear, tenureMonth ->
        return@combine amount.isNotEmpty() and rate.isNotEmpty() and (tenureYear.isNotEmpty()
                or tenureMonth.isNotEmpty())
    }

    private val _uiSharedFlow =
        MutableSharedFlow<Emi>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val uiSharedFlow = _uiSharedFlow.asSharedFlow()

    fun calculateEmi() {
        viewModelScope.launch {
            emiRepository.calculateEmi(
                amount.value,
                rate.value,
                tenureYear.value,
                tenureMonth.value
            ).collect {
                _uiSharedFlow.emit(it)
            }
        }
    }

    fun saveRecord() {
        viewModelScope.launch {
            dataStoreRepository.savePreference(
                EmiPreferences(
                    amount = amount.value,
                    rate = rate.value,
                    year = tenureYear.value,
                    month = tenureMonth.value
                )
            )
        }
    }

    fun getRecord() = dataStoreRepository.getPreferences
}