package com.emi.calculator.utils

import com.emi.calculator.common.Constants
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.math.RoundingMode

fun String.Companion.roundTo(value: Double): String? {
    return try {
        BigDecimal(value).setScale(2, RoundingMode.UP).toPlainString()
    } catch (e: NumberFormatException) {
        Constants.EXCEPTION_MESSAGE
    } catch (e: Exception) {
        e.message
    }
}

fun Double.Companion.calculateMonth(tenureYear: Double, tenureMonth: Double): Double {
    return if (tenureYear > 0) {
        (tenureYear * 12) + tenureMonth
    } else {
        tenureMonth
    }
}