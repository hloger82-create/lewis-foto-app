package com.lewisfoto.app.util

import com.lewisfoto.app.data.Sighting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object EbirdService {

    private const val BASE = "https://api.ebird.org/v2"
    private const val CENTER_LAT = 57.98
    private const val CENTER_LON = -6.75
    private const val RADIUS_KM = 50

    suspend fun recentSightings(apiKey: String, daysBack: Int = 3, maxResults: Int = 200): Result<List<Sighting>> =
        withContext(Dispatchers.IO) {
            runCatching {
                if (apiKey.isBlank()) error("Kein eBird-API-Key hinterlegt (Einstellungen).")
                val url = URL(
                    "$BASE/data/obs/geo/recent?lat=$CENTER_LAT&lng=$CENTER_LON" +
                        "&dist=$RADIUS_KM&back=$daysBack&maxResults=$maxResults" +
                        "&sppLocale=de&includeProvisional=true&detail=simple"
                )
                val conn = url.openConnection() as HttpURLConnection
                conn.setRequestProperty("X-eBirdApiToken", apiKey)
                conn.connectTimeout = 10000
                conn.readTimeout = 15000
                if (conn.responseCode != 200) {
                    error("eBird HTTP ${conn.responseCode}")
                }
                val arr: JSONArray = JSONArray(conn.inputStream.bufferedReader().readText())
                (0 until arr.length()).map { i ->
                    val o: JSONObject = arr.getJSONObject(i)
                    Sighting(
                        speciesCode = o.getString("speciesCode"),
                        commonName = o.optString("comName", o.getString("speciesCode")),
                        scientificName = o.getString("sciName"),
                        lat = o.getDouble("lat"),
                        lon = o.getDouble("lng"),
                        locationName = o.optString("locName", ""),
                        observedAt = runCatching {
                            LocalDateTime.parse(o.getString("obsDt"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                        }.getOrElse { LocalDateTime.now() },
                        count = if (o.has("howMany") && !o.isNull("howMany")) o.getInt("howMany") else null
                    )
                }
            }
        }

    suspend fun notableSightings(apiKey: String, daysBack: Int = 3): Result<List<Sighting>> =
        withContext(Dispatchers.IO) {
            runCatching {
                if (apiKey.isBlank()) error("Kein eBird-API-Key hinterlegt (Einstellungen).")
                val url = URL(
                    "$BASE/data/obs/geo/recent/notable?lat=$CENTER_LAT&lng=$CENTER_LON" +
                        "&dist=$RADIUS_KM&back=$daysBack&maxResults=100&sppLocale=de&detail=simple"
                )
                val conn = url.openConnection() as HttpURLConnection
                conn.setRequestProperty("X-eBirdApiToken", apiKey)
                conn.connectTimeout = 10000
                conn.readTimeout = 15000
                if (conn.responseCode != 200) error("eBird HTTP ${conn.responseCode}")
                val arr: JSONArray = JSONArray(conn.inputStream.bufferedReader().readText())
                (0 until arr.length()).map { i ->
                    val o: JSONObject = arr.getJSONObject(i)
                    Sighting(
                        speciesCode = o.getString("speciesCode"),
                        commonName = o.optString("comName", o.getString("speciesCode")),
                        scientificName = o.getString("sciName"),
                        lat = o.getDouble("lat"),
                        lon = o.getDouble("lng"),
                        locationName = o.optString("locName", ""),
                        observedAt = runCatching {
                            LocalDateTime.parse(o.getString("obsDt"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                        }.getOrElse { LocalDateTime.now() },
                        count = if (o.has("howMany") && !o.isNull("howMany")) o.getInt("howMany") else null
                    )
                }
            }
        }
}
