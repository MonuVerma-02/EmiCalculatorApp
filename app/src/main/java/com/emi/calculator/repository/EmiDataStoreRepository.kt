package com.emi.calculator.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import com.emi.calculator.data.EmiPreferences
import com.emi.calculator.extension.empty
import com.emi.calculator.preferences.PreferenceConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


import javax.inject.Inject

class EmiDataStoreRepository @Inject constructor(context: Context) {

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PreferenceConstants.DATASTORE_NAME
    )

    suspend fun savePreference(emiPreferences: EmiPreferences) {
        dataStore.edit { preferences ->
            preferences[PreferenceConstants.AMOUNT] = emiPreferences.amount
            preferences[PreferenceConstants.RATE] = emiPreferences.rate
            preferences[PreferenceConstants.YEAR] = emiPreferences.year
            preferences[PreferenceConstants.MONTH] = emiPreferences.month
        }
    }

    val getPreferences: Flow<EmiPreferences> = dataStore.data
        .map { preferences ->
            val amount = preferences[PreferenceConstants.AMOUNT] ?: String.empty()
            val rate = preferences[PreferenceConstants.RATE] ?: String.empty()
            val year = preferences[PreferenceConstants.YEAR] ?: String.empty()
            val month = preferences[PreferenceConstants.MONTH] ?: String.empty()
            EmiPreferences(amount, rate, year, month)
        }

}