package com.lewisfoto.app.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lewisfoto.app.data.Motif
import com.lewisfoto.app.data.UserSpot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private val Context.userSpotStore: DataStore<Preferences> by preferencesDataStore(name = "lewisfoto_user_spots")

object UserSpotStore {

    private val KEY = stringPreferencesKey("user_spots_json")

    fun all(context: Context): Flow<List<UserSpot>> = context.userSpotStore.data.map { prefs ->
        val raw = prefs[KEY] ?: "[]"
        runCatching {
            JSONArray(raw).let { arr ->
                (0 until arr.length()).map { i ->
                    val o = arr.getJSONObject(i)
                    UserSpot(
                        id = o.getString("id"),
                        name = o.getString("name"),
                        motif = Motif.fromName(o.optString("motif")) ?: Motif.OTHER,
                        lat = o.getDouble("lat"),
                        lon = o.getDouble("lon"),
                        description = o.optString("description"),
                        photoPath = o.optString("photoPath").ifEmpty { null },
                        osmInfo = o.optString("osmInfo"),
                        suggestedSpot = o.optString("suggestedSpot"),
                        createdAt = o.optLong("createdAt")
                    )
                }
            }
        }.getOrDefault(emptyList())
    }

    suspend fun add(context: Context, spot: UserSpot) {
        context.userSpotStore.edit { prefs ->
            val arr = JSONArray(prefs[KEY] ?: "[]")
            arr.put(JSONObject().apply {
                put("id", spot.id); put("name", spot.name); put("motif", spot.motif.name)
                put("lat", spot.lat); put("lon", spot.lon)
                put("description", spot.description)
                put("photoPath", spot.photoPath ?: "")
                put("osmInfo", spot.osmInfo)
                put("suggestedSpot", spot.suggestedSpot)
                put("createdAt", spot.createdAt)
            })
            prefs[KEY] = arr.toString()
        }
    }

    suspend fun remove(context: Context, id: String) {
        context.userSpotStore.edit { prefs ->
            val arr = JSONArray(prefs[KEY] ?: "[]")
            val out = JSONArray()
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                if (o.getString("id") != id) out.put(o)
            }
            prefs[KEY] = out.toString()
        }
    }
}
