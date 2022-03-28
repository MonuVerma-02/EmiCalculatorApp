package com.emi.calculator.preferences

import androidx.datastore.preferences.core.preferencesKey

object PreferenceConstants {
    const val DATASTORE_NAME = "emi_preferences"

    val AMOUNT = preferencesKey<String>("amount")
    val RATE = preferencesKey<String>("rate")
    val YEAR = preferencesKey<String>("year")
    val MONTH = preferencesKey<String>("month")
}