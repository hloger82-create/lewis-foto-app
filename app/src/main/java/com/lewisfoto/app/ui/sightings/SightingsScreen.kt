package com.lewisfoto.app.ui.sightings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lewisfoto.app.data.Sighting
import com.lewisfoto.app.util.EbirdService
import com.lewisfoto.app.util.GeoUtils
import com.lewisfoto.app.viewmodel.AppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SightingsScreen(viewModel: AppViewModel) {
    val apiKey by viewModel.ebirdApiKey.collectAsStateWithLifecycle()
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var all by remember { mutableStateOf<List<Sighting>>(emptyList()) }
    var notable by remember { mutableStateOf<List<Sighting>>(emptyList()) }
    var showNotableOnly by remember { mutableStateOf(false) }

    fun load() {
        if (apiKey.isBlank()) {
            error = "Kein eBird-API-Key hinterlegt – bitte in den Einstellungen eintragen (ebird.org → Account → API Key)."
            return
        }
        loading = true
        error = null
        CoroutineScope(Dispatchers.Main).launch {
            val res = EbirdService.recentSightings(apiKey, daysBack = 3)
            val resN = EbirdService.notableSightings(apiKey, daysBack = 3)
            loading = false
            res.onSuccess { all = it.sortedByDescending { s -> s.observedAt } }
                .onFailure { error = it.message }
            resN.onSuccess { notable = it }
        }
    }

    LaunchedEffect(apiKey) { if (apiKey.isNotBlank()) load() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sichtungen (eBird, letzte 3 Tage)", maxLines = 1, overflow = TextOverflow.Ellipsis) },
                actions = {
                    IconButton(onClick = { load() }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Aktualisieren")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(12.dp)) {
            if (apiKey.isBlank()) {
                Text(
                    "Ohne eBird-API-Key keine Live-Daten. Key kostenlos auf ebird.org holen und in den Einstellungen speichern.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (loading) {
                Box(Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            if (error != null) {
                Text(error!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            }
            if (notable.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Star, null, tint = Color(0xFFB07C1F), modifier = Modifier.size(18.dp))
                    Text(
                        " ${notable.size} seltene/bemerkenswerte Arten in 60 km Umkreis",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            val shown = if (showNotableOnly) notable else all
            Text(
                "${shown.size} angezeigt · ${all.size} gesamt",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 6.dp)
            )
            if (notable.isNotEmpty()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = !showNotableOnly,
                        onClick = { showNotableOnly = false },
                        label = { Text("Alle") }
                    )
                    FilterChip(
                        selected = showNotableOnly,
                        onClick = { showNotableOnly = true },
                        label = { Text("Nur seltene") }
                    )
                }
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(shown, key = { it.speciesCode + it.locationName + it.observedAt }) { s ->
                    SightingCard(s)
                }
            }
        }
    }
}

@Composable
private fun SightingCard(s: Sighting) {
    val ctx = LocalContext.current
    val df = DateTimeFormatter.ofPattern("dd.MM. HH:mm", Locale.GERMAN)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { GeoUtils.openNavigation(ctx, s.lat, s.lon, s.commonName) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2E8CA3).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    s.commonName.take(1),
                    color = Color(0xFF14657A),
                    fontWeight = FontWeight.Bold
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    s.commonName + (s.count?.let { " (${it}x)" } ?: ""),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "${s.scientificName} · ${s.locationName}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    s.observedAt.format(df) + " Uhr · " + String.format(Locale.GERMAN, "%.4f", s.lat) + ", " + String.format(Locale.GERMAN, "%.4f", s.lon),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
