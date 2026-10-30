package com.lewisfoto.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lewisfoto.app.data.FotoSpot
import com.lewisfoto.app.data.Motif
import com.lewisfoto.app.data.Season
import com.lewisfoto.app.data.SeasonNotes
import com.lewisfoto.app.data.SpotRepository
import com.lewisfoto.app.util.AppPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = AppPreferences(application)

    val motifs: List<Motif> = Motif.entries
    val dataSources = SpotRepository.dataSources

    val tripDate: StateFlow<LocalDate> = prefs.tripDate
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LocalDate.now())

    val darkMode: StateFlow<Boolean> = prefs.darkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val favorites: StateFlow<Set<String>> = prefs.favorites
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val season: StateFlow<Season> = tripDate
        .map { Season.of(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Season.of(LocalDate.now()))

    val seasonNotes: StateFlow<List<String>> = tripDate
        .map { SeasonNotes.notesFor(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SeasonNotes.notesFor(LocalDate.now()))

    private val _selectedMotif = MutableStateFlow<Motif?>(null)
    val selectedMotif: StateFlow<Motif?> = _selectedMotif

    fun spotsForMotif(motif: Motif): List<FotoSpot> = SpotRepository.spotsForMotif(motif)

    fun spotById(id: String): FotoSpot? = SpotRepository.spotById(id)

    fun favoriteSpots(): List<FotoSpot> = favorites.value.mapNotNull { SpotRepository.spotById(it) }

    fun selectMotif(motif: Motif?) { _selectedMotif.value = motif }

    fun setTripDate(date: LocalDate) = viewModelScope.launch { prefs.setTripDate(date) }

    fun setDarkMode(enabled: Boolean) = viewModelScope.launch { prefs.setDarkMode(enabled) }

    fun toggleFavorite(spotId: String) = viewModelScope.launch { prefs.toggleFavorite(spotId) }
}
