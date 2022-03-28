package com.emi.calculator.data

import com.emi.calculator.extension.defaultValue

data class Emi(
    val emi: Double = Double.defaultValue(),
    val interest: Double = Double.defaultValue(),
    val totalAmount: Double = Double.defaultValue()
)
