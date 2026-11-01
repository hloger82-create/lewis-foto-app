package com.lewisfoto.app.data

data class UserSpot(
    val id: String,
    val name: String,
    val motif: Motif,
    val lat: Double,
    val lon: Double,
    val description: String = "",
    val photoPath: String? = null,
    val osmInfo: String = "",           // Reverse-Geocoding (Ortsteil/Straße)
    val suggestedSpot: String = "",     // nächster bekannter Spot
    val bestTimeOfDay: String = "",
    val tips: List<String> = emptyList(),
    val safety: List<String> = emptyList(),
    val parkingName: String = "",
    val parkingNote: String = "",
    val researched: Boolean = false,    // Zusatzinfos per Internet-Suche befüllt
    val createdAt: Long = 0L
)
