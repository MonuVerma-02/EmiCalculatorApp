package com.emi.calculator.repository

import com.emi.calculator.data.Emi
import com.emi.calculator.extension.emptyOrNot
import com.emi.calculator.utils.calculateMonth
import kotlinx.coroutines.flow.flow
import kotlin.math.pow

class EmiRepository {
    fun calculateEmi(amount: String, rate: String, year: String, month: String) = flow {
        val p = amount.toDouble()

        val _year = String.emptyOrNot(year)
        val _month = String.emptyOrNot(month)
        val n = Double.calculateMonth(_year?.let { _year.toDouble() } ?: 0.0,
            _month?.let { _month.toDouble() } ?: 0.0)

        val _r = rate.toDouble()
        val r = ((_r / 12) / 100)

        val emi = (((p * r * ((1 + r).pow(n)))) /
                (((1 + r).pow(n)) - 1))

        val totalAmt = (emi * n)
        val interest = (totalAmt - p)

        emit(Emi(emi, interest, totalAmt))
    }
}