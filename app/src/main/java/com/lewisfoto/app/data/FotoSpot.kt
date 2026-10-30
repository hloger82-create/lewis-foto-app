package com.lewisfoto.app.data

data class ParkingInfo(
    val name: String,
    val note: String,
    val distanceToSpot: String = ""
)

data class TideInfo(
    val bestAroundLowTide: Boolean,
    val note: String,
    val relevant: Boolean = true
)

data class Rating(
    val season: Season,
    val probability: Int
)

data class FotoSpot(
    val id: String,
    val name: String,
    val motif: Motif,
    val lat: Double,
    val lon: Double,
    val what3words: String? = null,
    val ratings: List<Rating>,
    val bestTimeOfDay: String,
    val tide: TideInfo?,
    val tips: List<String>,
    val safety: List<String>,
    val parking: ParkingInfo,
    val sources: List<String>
) {
    fun ratingFor(season: Season): Rating? = ratings.firstOrNull { it.season == season }
}
