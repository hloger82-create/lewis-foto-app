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
                (0 until arr.length()).map { i -> parse(arr.getJSONObject(i)) }
            }
        }.getOrDefault(emptyList())
    }

    private fun parse(o: JSONObject) = UserSpot(
        id = o.getString("id"),
        name = o.getString("name"),
        motif = Motif.fromName(o.optString("motif")) ?: Motif.OTHER,
        lat = o.getDouble("lat"),
        lon = o.getDouble("lon"),
        description = o.optString("description"),
        photoPath = o.optString("photoPath").ifEmpty { null },
        osmInfo = o.optString("osmInfo"),
        suggestedSpot = o.optString("suggestedSpot"),
        bestTimeOfDay = o.optString("bestTimeOfDay"),
        tips = o.optJSONArray("tips")?.let { a -> (0 until a.length()).map { a.getString(it) } } ?: emptyList(),
        safety = o.optJSONArray("safety")?.let { a -> (0 until a.length()).map { a.getString(it) } } ?: emptyList(),
        parkingName = o.optString("parkingName"),
        parkingNote = o.optString("parkingNote"),
        researched = o.optBoolean("researched"),
        createdAt = o.optLong("createdAt")
    )

    private fun toJson(s: UserSpot) = JSONObject().apply {
        put("id", s.id); put("name", s.name); put("motif", s.motif.name)
        put("lat", s.lat); put("lon", s.lon)
        put("description", s.description)
        put("photoPath", s.photoPath ?: "")
        put("osmInfo", s.osmInfo)
        put("suggestedSpot", s.suggestedSpot)
        put("bestTimeOfDay", s.bestTimeOfDay)
        put("tips", JSONArray(s.tips))
        put("safety", JSONArray(s.safety))
        put("parkingName", s.parkingName)
        put("parkingNote", s.parkingNote)
        put("researched", s.researched)
        put("createdAt", s.createdAt)
    }

    suspend fun add(context: Context, spot: UserSpot) {
        context.userSpotStore.edit { prefs ->
            val arr = JSONArray(prefs[KEY] ?: "[]")
            arr.put(toJson(spot))
            prefs[KEY] = arr.toString()
        }
    }

    suspend fun update(context: Context, spot: UserSpot) {
        context.userSpotStore.edit { prefs ->
            val arr = JSONArray(prefs[KEY] ?: "[]")
            val out = JSONArray()
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                out.put(if (o.getString("id") == spot.id) toJson(spot) else o)
            }
            prefs[KEY] = out.toString()
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
