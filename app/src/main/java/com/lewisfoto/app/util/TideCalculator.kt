package com.lewisfoto.app.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.math.PI
import kotlin.math.cos

data class TideEvent(val time: LocalDateTime, val high: Boolean)

object TideCalculator {

    private val ZONE = ZoneId.of("Europe/London")

    private const val SYNODIC = 29.530588853
    private const val M2 = 12.4206012
    private const val S2 = 12.0

    private const val LUNAR_PHASE_2026_01_01 = 1767225600L

    fun phaseAngle(dateTime: LocalDateTime): Double {
        val ms = dateTime.atZone(ZONE).toInstant().toEpochMilli()
        val daysSince = (ms - LUNAR_PHASE_2026_01_01) / 86400_000.0
        val phase = (daysSince % SYNODIC) / SYNODIC
        return phase * 2 * PI
    }

    fun eventsFor(date: LocalDate, lat: Double, lon: Double): List<TideEvent> {
        val zone = ZONE
        val events = mutableListOf<TideEvent>()
        val dayStart = date.atStartOfDay(zone).toLocalDateTime()
        val lonCorrHours = lon / 15.0
        var t = dayStart.minusHours(8)
        val end = dayStart.plusDays(2)
        while (t.isBefore(end)) {
            if (isLocalExtremum(t, lat, lon)) {
                val high = tideHeight(t, lat, lon) > tideHeight(t.minusMinutes(10), lat, lon) &&
                    tideHeight(t, lat, lon) > tideHeight(t.plusMinutes(10), lat, lon)
                val last = events.lastOrNull()
                if (last == null || last.high != high) {
                    events.add(TideEvent(t, high))
                }
            }
            t = t.plusMinutes(10)
        }
        return events.take(4)
    }

    private fun hoursOfDay(t: LocalDateTime): Double = t.hour + t.minute / 60.0 + t.second / 3600.0

    private fun tideHeight(t: LocalDateTime, lat: Double, lon: Double): Double {
        val phase = phaseAngle(t)
        val lonCorr = lon / 15.0 * 2 * PI / 12.0
        val m2 = 2 * PI * hoursOfDay(t) / M2 + lonCorr
        val s2 = 2 * PI * hoursOfDay(t) / S2 + lonCorr
        val latFactor = cos(lat * PI / 180.0)
        return 2.0 * cos(m2 + phase) * latFactor + 0.6 * cos(s2 + phase) + 0.3 * cos(phase)
    }

    private fun isLocalExtremum(t: LocalDateTime, lat: Double, lon: Double): Boolean {
        val h0 = tideHeight(t, lat, lon)
        val hPrev = tideHeight(t.minusMinutes(10), lat, lon)
        val hNext = tideHeight(t.plusMinutes(10), lat, lon)
        return (h0 > hPrev && h0 > hNext) || (h0 < hPrev && h0 < hNext)
    }

    fun nextEvents(lat: Double, lon: Double, count: Int = 4): List<TideEvent> {
        val now = LocalDateTime.now(ZONE)
        val today = now.toLocalDate()
        return (eventsFor(today, lat, lon) + eventsFor(today.plusDays(1), lat, lon))
            .filter { it.time.isAfter(now.minusMinutes(30)) }
            .sortedBy { it.time }
            .take(count)
    }
}
