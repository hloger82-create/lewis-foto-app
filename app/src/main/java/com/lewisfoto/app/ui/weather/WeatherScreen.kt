package com.lewisfoto.app.ui.weather

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.lewisfoto.app.data.DailyWeather
import com.lewisfoto.app.data.HourlyWeather
import com.lewisfoto.app.data.SunCalculator
import com.lewisfoto.app.util.WeatherService
import com.lewisfoto.app.viewmodel.AppViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: AppViewModel) {
    val lat = 57.98
    val lon = -6.75
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var dailies by remember { mutableStateOf<List<DailyWeather>>(emptyList()) }
    var hours by remember { mutableStateOf<List<HourlyWeather>>(emptyList()) }

    LaunchedEffect(Unit) {
        WeatherService.fetchForecast(lat, lon).fold(
            onSuccess = { (d, h) -> dailies = d; hours = h; loading = false },
            onFailure = { error = it.message ?: "Unbekannter Fehler"; loading = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wetter & Licht", maxLines = 1, overflow = TextOverflow.Ellipsis) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        if (loading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (error != null) {
                item {
                    Text(
                        "Wetter konnte nicht geladen werden: $error\n(Internet prüfen – Daten kommen von Open-Meteo)",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            item {
                Text("Tage (Sonnenauf-/untergang, Goldene/Blaue Stunde)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            }
            items(dailies) { d ->
                val sun = SunCalculator.forDate(d.date, lat, lon)
                val df = DateTimeFormatter.ofPattern("EEE dd.MM.", Locale.GERMAN)
                val tf = DateTimeFormatter.ofPattern("HH:mm")
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.WbSunny, null, tint = Color(0xFFE8A33D), modifier = Modifier.size(18.dp))
                            Text(" ${d.date.format(df)}  ·  ${d.tempMinC.toInt()}°/${d.tempMaxC.toInt()}°  ·  ${d.precipitationMm} mm  ·  Wind max ${d.maxWindKmh.toInt()} km/h  ·  Wolken ${d.cloudCoverMeanPct} %",
                                style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, maxLines = 2)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.WbTwilight, null, tint = Color(0xFFB07C1F), modifier = Modifier.size(16.dp))
                            Text(" Sonne ${sun.sunrise.format(tf)}–${sun.sunset.format(tf)} · Goldene Stunde ${sun.goldenHourMorningEnd.format(tf)} / ${sun.goldenHourEveningStart.format(tf)} Uhr",
                                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.WbTwilight, null, tint = Color(0xFF2E5C8A), modifier = Modifier.size(16.dp))
                            Text(" Blaue Stunde ${sun.blueHourMorningStart.format(tf)} / ${sun.blueHourEveningEnd.format(tf)} Uhr",
                                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        val best = hours.filter { it.time.toLocalDate() == d.date && it.isGoldenHourCandidate }
                        if (best.isNotEmpty()) {
                            val bestHour = best.maxByOrNull { it.cloudCoverPct }
                            Text(
                                "📸 Foto-Tipp: beste Stunde heute ca. ${bestHour?.time?.format(DateTimeFormatter.ofPattern("HH:mm"))} Uhr (${bestHour?.cloudCoverPct}% Wolken, ${bestHour?.labelDe})",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2E7D32),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            item {
                Text("Nächste 24 Stunden (stündlich)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            }
            item {
                Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    hours.take(24).forEach { h ->
                        HourChip(h)
                    }
                }
            }

            item {
                Text(
                    "Daten: Open-Meteo (CC BY 4.0), Sonnenstände lokal berechnet. Regenradar als Overlay auf der Karte (RainViewer).",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun HourChip(h: HourlyWeather) {
    val tf = DateTimeFormatter.ofPattern("HH:mm")
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (h.isGoldenHourCandidate) Color(0xFFE8F3E4) else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(h.time.format(tf), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Icon(
                when {
                    h.precipitationMm >= 0.5 -> Icons.Filled.Umbrella
                    h.cloudCoverPct > 60 -> Icons.Filled.Cloud
                    else -> Icons.Filled.WbSunny
                },
                null,
                tint = if (h.isGoldenHourCandidate) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
            Text("${h.temperatureC.toInt()}°", style = MaterialTheme.typography.labelSmall)
            Text("☁ ${h.cloudCoverPct}%", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (h.precipitationMm > 0.0) Text("🌧 ${h.precipitationMm}mm", style = MaterialTheme.typography.labelSmall, color = Color(0xFF2563EB))
            Text("💨 ${h.windSpeedKmh.toInt()}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
