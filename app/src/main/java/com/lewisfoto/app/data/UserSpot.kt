package com.lewisfoto.app.data

data class UserSpot(
    val id: String,
    val name: String,
    val motif: Motif,
    val lat: Double,
    val lon: Double,
    val description: String = "",
    val photoPath: String? = null,
    val osmInfo: String = "",          // Reverse-Geocoding-Ergebnis (Ortsteil/Straße)
    val suggestedSpot: String = "",    // nächster bekannter Spot als Vorschlag
    val createdAt: Long = 0L
)
