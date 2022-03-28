package com.emi.calculator.extension

fun Double.Companion.defaultValue() = 0.0

fun String.Companion.empty() = ""

fun String.Companion.emptyOrNot(value: String): String? {
    return if (value == "") null else value
}
