package com.lewisfoto.app.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.net.URLEncoder

object ReverseGeocode {

    suspend fun describe(lat: Double, lon: Double): String = withContext(Dispatchers.IO) {
        runCatching {
            val url = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=$lat&lon=$lon&zoom=16&addressdetails=1"
            val req = URL(url).openConnection().apply {
                setRequestProperty("User-Agent", "lewis-foto-app/1.0 (Android)")
                connectTimeout = 8000
                readTimeout = 8000
            }
            val json = JSONObject(req.getInputStream().bufferedReader().readText())
            val a = json.optJSONObject("address") ?: return@runCatching ""
            listOfNotNull(
                a.optString("suburb").ifEmpty { null },
                a.optString("village").ifEmpty { null },
                a.optString("hamlet").ifEmpty { null },
                a.optString("road").ifEmpty { null },
                a.optString("town").ifEmpty { null },
                a.optString("county").ifEmpty { null }
            ).distinct().take(3).joinToString(", ")
        }.getOrDefault("")
    }
}
