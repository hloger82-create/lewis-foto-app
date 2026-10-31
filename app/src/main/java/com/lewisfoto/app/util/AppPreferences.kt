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
        private val CUSTOM_COORDS = stringPreferencesKey("custom_coords")
        private val EBIRD_API_KEY = stringPreferencesKey("ebird_api_key")
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

    val ebirdApiKey: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[EBIRD_API_KEY] ?: ""
    }

    suspend fun setEbirdApiKey(key: String) {
        context.dataStore.edit { it[EBIRD_API_KEY] = key.trim() }
    }

    val customCoords: Flow<Map<String, Pair<Double, Double>>> = context.dataStore.data.map { prefs ->
        (prefs[CUSTOM_COORDS] ?: "").split(";").filter { it.isNotBlank() }.mapNotNull { entry ->
            val parts = entry.split("=")
            val coordParts = parts.getOrNull(1)?.split(",") ?: return@mapNotNull null
            val lat = coordParts.getOrNull(0)?.toDoubleOrNull() ?: return@mapNotNull null
            val lon = coordParts.getOrNull(1)?.toDoubleOrNull() ?: return@mapNotNull null
            parts[0] to (lat to lon)
        }.toMap()
    }

    suspend fun setTripDate(date: LocalDate) {
        context.dataStore.edit { it[TRIP_DATE] = date.toEpochDay() }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[DARK_MODE] = enabled }
    }

    suspend fun setCustomCoords(spotId: String, lat: Double, lon: Double) {
        context.dataStore.edit { prefs ->
            val current = (prefs[CUSTOM_COORDS] ?: "").split(";").filter { it.isNotBlank() && !it.startsWith("$spotId=") }.toMutableList()
            current.add("$spotId=$lat,$lon")
            prefs[CUSTOM_COORDS] = current.joinToString(";")
        }
    }

    suspend fun resetCustomCoords(spotId: String) {
        context.dataStore.edit { prefs ->
            val current = (prefs[CUSTOM_COORDS] ?: "").split(";").filter { it.isNotBlank() && !it.startsWith("$spotId=") }
            prefs[CUSTOM_COORDS] = current.joinToString(";")
        }
    }

    suspend fun toggleFavorite(spotId: String) {
        context.dataStore.edit { prefs ->
            val current = (prefs[FAVORITES] ?: "").split(",").filter { it.isNotBlank() }.toMutableSet()
            if (spotId in current) current.remove(spotId) else current.add(spotId)
            prefs[FAVORITES] = current.joinToString(",")
        }
    }
}
