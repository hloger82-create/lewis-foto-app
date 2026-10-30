package com.lewisfoto.app.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "lewisfoto_prefs")

class AppPreferences(private val context: Context) {

    companion object {
        private val TRIP_DATE = longPreferencesKey("trip_date_epoch_day")
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
        private val FAVORITES = stringPreferencesKey("favorite_spot_ids")
    }

    val tripDate: Flow<LocalDate> = context.dataStore.data.map { prefs ->
        val epochDay = prefs[TRIP_DATE] ?: LocalDate.now().toEpochDay()
        LocalDate.ofEpochDay(epochDay)
    }

    val darkMode: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[DARK_MODE] ?: false
    }

    val favorites: Flow<Set<String>> = context.dataStore.data.map { prefs ->
        (prefs[FAVORITES] ?: "").split(",").filter { it.isNotBlank() }.toSet()
    }

    suspend fun setTripDate(date: LocalDate) {
        context.dataStore.edit { it[TRIP_DATE] = date.toEpochDay() }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[DARK_MODE] = enabled }
    }

    suspend fun toggleFavorite(spotId: String) {
        context.dataStore.edit { prefs ->
            val current = (prefs[FAVORITES] ?: "").split(",").filter { it.isNotBlank() }.toMutableSet()
            if (spotId in current) current.remove(spotId) else current.add(spotId)
            prefs[FAVORITES] = current.joinToString(",")
        }
    }
}
