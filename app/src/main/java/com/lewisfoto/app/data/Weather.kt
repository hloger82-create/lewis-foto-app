package com.lewisfoto.app.data

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

data class HourlyWeather(
    val time: LocalDateTime,
    val temperatureC: Double,
    val cloudCoverPct: Int,
    val precipitationMm: Double,
    val windSpeedKmh: Double,
    val weatherCode: Int
) {
    val isGoldenHourCandidate: Boolean
        get() = cloudCoverPct in 15..75 && precipitationMm < 0.2 && windSpeedKmh < 40

    val labelDe: String
        get() = when (weatherCode) {
            0 -> "klar"
            1, 2 -> "fast klar"
            3 -> "bewölkt"
            45, 48 -> "Nebel"
            in 51..57 -> "Nieselregen"
            in 61..67 -> "Regen"
            in 71..77 -> "Schnee"
            in 80..82 -> "Schauer"
            in 85..86 -> "Schneeschauer"
            95, 96, 99 -> "Gewitter"
            else -> "wechselhaft"
        }
}

data class DailyWeather(
    val date: LocalDate,
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime,
    val tempMinC: Double,
    val tempMaxC: Double,
    val precipitationMm: Double,
    val maxWindKmh: Double,
    val cloudCoverMeanPct: Int
)

data class SunTimes(
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime,
    val goldenHourMorningEnd: LocalDateTime,
    val goldenHourEveningStart: LocalDateTime,
    val blueHourMorningStart: LocalDateTime,
    val blueHourEveningEnd: LocalDateTime
)

object SunCalculator {

    private const val ZENITH_OFFICIAL = 90.833

    fun forDate(date: LocalDate, lat: Double, lon: Double): SunTimes {
        val zone = ZoneId.of("Europe/London")
        val rise = compute(date, lat, lon, true, zone)
        val set = compute(date, lat, lon, false, zone)
        return SunTimes(
            sunrise = rise,
            sunset = set,
            goldenHourMorningEnd = rise.plusMinutes(60),
            goldenHourEveningStart = set.minusMinutes(60),
            blueHourMorningStart = rise.minusMinutes(40),
            blueHourEveningEnd = set.plusMinutes(40)
        )
    }

    private fun compute(date: LocalDate, lat: Double, lon: Double, sunrise: Boolean, zone: ZoneId): LocalDateTime {
        val n = date.toEpochDay() - LocalDate.of(2000, 1, 1).toEpochDay() + 0.0009
        val jStar = n - lon / 360.0
        val m = (357.5291 + 0.98560028 * jStar) * Math.PI / 180.0
        val c = 1.9148 * sin(m) + 0.02 * sin(2 * m) + 0.0003 * sin(3 * m)
        val lambda = (280.4665 + 0.98564736 * jStar + c) * Math.PI / 180.0
        val decl = kotlin.math.asin(sin(lambda) * sin(23.4397 * Math.PI / 180.0))
        val latRad = lat * Math.PI / 180.0
        val cosH = (cos(ZENITH_OFFICIAL * Math.PI / 180.0) - sin(latRad) * sin(decl)) /
            (cos(latRad) * cos(decl))
        val h = if (sunrise) 360.0 - Math.toDegrees(acos(cosH.coerceIn(-1.0, 1.0)))
                else Math.toDegrees(acos(cosH.coerceIn(-1.0, 1.0)))
        val jTransit = 2451545.0 + jStar + 0.0053 * sin(m) - 0.0069 * sin(2 * lambda)
        val jEvent = jTransit + h / 360.0
        val jDelta = jEvent - 2451545.0
        val epochMillis = (jDelta * 86400.0 * 1000.0).toLong() + LocalDateTime.of(2000, 1, 1, 12, 0)
            .atZone(ZoneId.of("UTC")).toInstant().toEpochMilli()
        return java.time.Instant.ofEpochMilli(epochMillis).atZone(zone).toLocalDateTime()
    }
}
