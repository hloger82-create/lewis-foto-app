package com.lewisfoto.app.data

data class ParkingInfo(
    val name: String,
    val note: String
)

data class ArrivalInfo(
    val fromStornoway: String,
    val description: String
)

data class TideInfo(
    val bestAroundLowTide: Boolean,
    val note: String
)

data class Rating(
    val season: Season,
    val frequency: Int,
    val probability: Int
)

data class FotoSpot(
    val id: String,
    val name: String,
    val motif: Motif,
    val lat: Double,
    val lon: Double,
    val ratings: List<Rating>,
    val bestTimeOfDay: String,
    val tide: TideInfo?,
    val tips: List<String>,
    val safety: List<String>,
    val parking: ParkingInfo,
    val arrival: ArrivalInfo,
    val sources: List<String>
) {
    fun ratingFor(season: Season): Rating? = ratings.firstOrNull { it.season == season }
}
