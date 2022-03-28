package com.emi.calculator.data

import com.emi.calculator.extension.empty

data class EmiPreferences(
    val amount: String = String.empty(),
    val rate: String = String.empty(),
    val year: String = String.empty(),
    val month: String = String.empty()
)