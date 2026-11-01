package com.lewisfoto.app.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.net.URLEncoder

/**
 * Hintergrund-Recherche für eigene Spots über offene APIs (kein Key nötig):
 * - Nominatim (OSM): Ortsangabe
 * - Overpass (OSM): nahegelegene Parkplätze / Viewpoints / Strände / Cafés (500 m)
 * - Wikipedia-Geosearch: nächste Sehenswürdigkeit in der Nähe
 */
object ResearchService {

    data class Result(
        val osmInfo: String,
        val bestTimeOfDay: String,
        val tips: List<String>,
        val safety: List<String>,
        val parkingName: String,
        val parkingNote: String
    )

    private suspend fun get(url: String, ua: String): String? = withContext(Dispatchers.IO) {
        runCatching {
            URL(url).openConnection().apply {
                setRequestProperty("User-Agent", ua)
                connectTimeout = 8000
                readTimeout = 8000
            }.getInputStream().bufferedReader().readText()
        }.getOrNull()
    }

    suspend fun research(lat: Double, lon: Double, motifLabel: String, nearestSpot: String): Result = withContext(Dispatchers.IO) {
        val ua = "lewis-foto-app/1.0 (Android; research)"
        val jobs = listOf(
            async { get("https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=$lat&lon=$lon&zoom=16&addressdetails=1", ua) },
            async { get("https://overpass-api.de/api/interpreter?data=" + URLEncoder.encode(
                "[out:json][timeout:10];(" +
                "node(around:500,$lat,$lon)[amenity=parking];" +
                "node(around:500,$lat,$lon)[tourism=viewpoint];" +
                "node(around:500,$lat,$lon)[natural=beach];" +
                "node(around:500,$lat,$lon)[amenity=cafe];" +
                "node(around:500,$lat,$lon)[leisure=picnic_site];" +
                ");out tags 20;", "UTF-8"), ua) },
            async { get("https://de.wikipedia.org/w/api.php?action=query&list=geosearch&gscoord=$lat|$lon&gsradius=2000&gslimit=3&format=json", ua) }
        )
        val results = jobs.awaitAll()

        // 1) Ortsangabe
        val osmInfo = runCatching {
            val a = JSONObject(results[0] ?: "{}").optJSONObject("address") ?: return@runCatching ""
            listOfNotNull(
                a.optString("suburb").ifEmpty { null },
                a.optString("village").ifEmpty { null },
                a.optString("hamlet").ifEmpty { null },
                a.optString("road").ifEmpty { null },
                a.optString("town").ifEmpty { null }
            ).distinct().take(3).joinToString(", ")
        }.getOrDefault("")

        // 2) Overpass-POIs
        val tips = mutableListOf<String>()
        val safety = mutableListOf<String>()
        var parkingName = ""
        var parkingNote = ""
        runCatching {
            val elements = JSONObject(results[1] ?: "{}").optJSONArray("elements") ?: return@runCatching
            var parkingCount = 0
            for (i in 0 until elements.length()) {
                val el = elements.getJSONObject(i)
                val tags = el.optJSONObject("tags") ?: continue
                val name = tags.optString("name")
                val amenity = tags.optString("amenity")
                val tourism = tags.optString("tourism")
                val natural = tags.optString("natural")
                when {
                    amenity == "parking" -> {
                        parkingCount++
                        if (parkingName.isEmpty()) {
                            parkingName = if (name.isNotBlank()) "Parkplatz $name" else "Parkplatz in der N\u00e4he (OSM)"
                            parkingNote = "ca. innerhalb 500 m laut OpenStreetMap"
                        }
                    }
                    tourism == "viewpoint" -> tips += "Aussichtspunkt${if (name.isNotBlank()) " \u201e$name\u201c" else ""} in der N\u00e4he (OSM)."
                    natural == "beach" -> tips += "Strand in der N\u00e4he (OSM) \u2013 Tide beachten."
                    amenity == "cafe" -> tips += "Caf\u00e9${if (name.isNotBlank()) " \u201e$name\u201c" else ""} in der N\u00e4he f\u00fcr Versorgung (OSM)."
                    tags.optString("leisure") == "picnic_site" -> tips += "Picknickplatz in der N\u00e4he (OSM)."
                }
            }
            if (parkingCount > 1) parkingNote += " ($parkingCount Parkpl\u00e4tze innerhalb 500 m)"
        }

        // 3) Wikipedia: nahegelegene Sehenswürdigkeit
        runCatching {
            val pages = JSONObject(results[2] ?: "{}").optJSONObject("query")?.optJSONArray("geosearch") ?: return@runCatching
            if (pages.length() > 0) {
                val t = pages.getJSONObject(0).optString("title")
                if (t.isNotBlank()) tips += "Sehenswert in der N\u00e4he: $t (Wikipedia)."
            }
        }

        if (nearestSpot.isNotBlank()) tips += "Bekannter Spot in der N\u00e4he: $nearestSpot."
        tips += "Motiv: $motifLabel \u2013 saisonale Wahrscheinlichkeit siehe Startseite."
        safety += "Allgemein: Abstand zu Wildtieren halten, keine Nester st\u00f6ren, Hunde an die Leine."
        safety += "Wetter und Tide vor Ort pr\u00fcfen (Tab \u201eWetter\u201c)."

        val bestTime = when (motifLabel) {
            "Otter" -> "D\u00e4mmerung (fr\u00fch morgens / abends), bei Ebbe aktiver"
            "Robben & Seehunde" -> "2 h vor/nach Niedrigwasser (Ebbe)"
            "Limikolen" -> "Bei Ebbe / um Niedrigwasser"
            "Hirsche" -> "Fr\u00fcher Morgen und Abendd\u00e4mmerung"
            "Wale & Delfine" -> "Bei ruhiger See, ganzt\u00e4gig beobachten"
            "Adler" -> "Vormittag bei Thermik und gutem Wetter"
            else -> "Goldene Stunde morgens/abends; Mittags hartes Licht meiden"
        }

        Result(
            osmInfo = osmInfo,
            bestTimeOfDay = bestTime,
            tips = tips.distinct().take(6),
            safety = safety.distinct().take(4),
            parkingName = parkingName,
            parkingNote = parkingNote
        )
    }
}
