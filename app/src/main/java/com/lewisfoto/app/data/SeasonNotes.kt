package com.lewisfoto.app.data

import java.time.LocalDate

object SeasonNotes {

    fun notesFor(date: LocalDate): List<String> {
        val month = date.monthValue
        val notes = mutableListOf<String>()
        when {
            month in 10..12 -> {
                notes += "Kegelrobben-Welpenzeit (Peak Okt–Dez): an abgelegenen Stränden Welpen möglich – immer Abstand halten."
                notes += "Winter-Watvögel treffen ein: Limikolen an den Stränden und Wattflächen zahlreich."
                notes += "Bis zu 800 Eistaucher (Great Northern Divers) können sich in der Broad Bay sammeln."
                notes += "Brunft der Rothirsche läuft bis ca. Mitte November aus – Hirsche noch aktiv, aber unberechenbar."
                notes += "Seevögel wie Papageitaucher und Lummen sind zu dieser Zeit abwesend."
                notes += "Minke-Wale sind im Winter selten; Schweinswale und Delfine bleiben realistisch (Tiumpan Head)."
                notes += "Bootstouren (z. B. zu den Shiant Isles) fahren meist nicht – vorab Verfügbarkeit prüfen."
            }
            month in 1..2 -> {
                notes += "Winter: Watvögel und Eistaucher in der Broad Bay, Schneehühner/Hasen in Weiß auf den Bergen."
                notes += "Kurze Tage: Sonnenaufgang spät, Sonnenuntergang früh – Tageslicht für Fotografie begrenzen."
                notes += "Stürme häufig: Westküste meiden bei starkem Wind, Klippenränder meiden."
            }
            month in 3..5 -> {
                notes += "Frühling: Zugvögel (Limikolen) ziehen durch – April/Mai beste Chancen an den Stränden."
                notes += "Papageitaucher auf den Shiant Isles (April–Juli), Bootstouren ab Kyle of Lochalsh/Uig."
                notes += "Kegelrobben gebären im Herbst – im Frühling noch Jungtiere an den Stränden möglich."
            }
            else -> {
                notes += "Sommer: beste Zeit für Bootstouren (Wale, Delfine, Papageitaucher auf den Shiants)."
                notes += "Brunft der Rothirsche September/Oktober – im Hochsommer Hirsche scheuer."
                notes += "Mücken (Midges) Juni–August an windstillen Stellen – Kopfnetz empfohlen."
                notes += "Lange Tage: Mitternachtssonneähnliche Lichtstimmung bis spät abends."
            }
        }
        return notes
    }
}
