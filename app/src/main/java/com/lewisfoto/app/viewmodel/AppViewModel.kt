package com.lewisfoto.app.viewmodel

import android.app.Application
import android.graphics.Bitmap
import com.lewisfoto.app.util.HomeBackground
import com.lewisfoto.app.util.UserSpotStore
import com.lewisfoto.app.data.UserSpot
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

    val ebirdApiKey: StateFlow<String> = prefs.ebirdApiKey
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val customCoords: StateFlow<Map<String, Pair<Double, Double>>> = prefs.customCoords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

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

    fun coordsOf(spot: FotoSpot): Pair<Double, Double> =
        customCoords.value[spot.id] ?: (spot.lat to spot.lon)

    fun setTripDate(date: LocalDate) = viewModelScope.launch { prefs.setTripDate(date) }

    fun setDarkMode(enabled: Boolean) = viewModelScope.launch { prefs.setDarkMode(enabled) }

    fun setEbirdApiKey(key: String) = viewModelScope.launch { prefs.setEbirdApiKey(key) }

    fun setCustomCoords(spotId: String, lat: Double, lon: Double) =
        viewModelScope.launch { prefs.setCustomCoords(spotId, lat, lon) }

    fun resetCustomCoords(spotId: String) =
        viewModelScope.launch { prefs.resetCustomCoords(spotId) }

    fun toggleFavorite(spotId: String) = viewModelScope.launch { prefs.toggleFavorite(spotId) }

    private val _homeBg = MutableStateFlow<Bitmap?>(null)
    val homeBg: StateFlow<Bitmap?> = _homeBg

    fun hasCustomHomeBg(): Boolean = HomeBackground.hasCustom(getApplication())

    fun reloadHomeBg() = viewModelScope.launch {
        _homeBg.value = HomeBackground.loadBitmap(getApplication())
    }

    fun clearHomeBg() = viewModelScope.launch {
        HomeBackground.clear(getApplication())
        _homeBg.value = null
    }

    val userSpots: StateFlow<List<UserSpot>> = UserSpotStore.all(getApplication())
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun allSpotsForMap() = SpotRepository.allSpots

    fun reloadUserSpots() { /* StateFlow aktualisiert sich ueber DataStore automatisch */ }
}
