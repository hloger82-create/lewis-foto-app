package com.lewisfoto.app.data

import java.time.LocalDateTime

data class Sighting(
    val speciesCode: String,
    val commonName: String,
    val scientificName: String,
    val lat: Double,
    val lon: Double,
    val locationName: String,
    val observedAt: LocalDateTime,
    val count: Int?
)
