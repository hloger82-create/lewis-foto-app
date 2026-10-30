package com.lewisfoto.app.data

import java.time.LocalDate
import java.time.Month

enum class Season(val labelDe: String) {
    SPRING("Frühling"),
    SUMMER("Sommer"),
    AUTUMN("Herbst"),
    WINTER("Winter");

    companion object {
        fun of(date: LocalDate): Season = when (date.month) {
            Month.MARCH, Month.APRIL, Month.MAY -> SPRING
            Month.JUNE, Month.JULY, Month.AUGUST -> SUMMER
            Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER -> AUTUMN
            else -> WINTER
        }
    }
}
