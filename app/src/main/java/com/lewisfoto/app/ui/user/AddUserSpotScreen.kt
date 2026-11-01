package com.lewisfoto.app.ui.user

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lewisfoto.app.data.Motif
import com.lewisfoto.app.util.ExifGeo
import com.lewisfoto.app.util.ReverseGeocode
import com.lewisfoto.app.util.ResearchService
import com.lewisfoto.app.util.UserSpotStore
import com.lewisfoto.app.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserSpotScreen(
    viewModel: AppViewModel,
    initialLat: Double?,
    initialLon: Double?,
    onDone: () -> Unit,
    onBack: () -> Unit
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var motif by remember { mutableStateOf<Motif?>(null) }
    var motifExpanded by remember { mutableStateOf(false) }
    var lat by remember { mutableStateOf(initialLat) }
    var lon by remember { mutableStateOf(initialLon) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var osmInfo by remember { mutableStateOf("") }
    var suggestion by remember { mutableStateOf("") }
    var status by remember { mutableStateOf<String?>(null) }
    var saving by remember { mutableStateOf(false) }
    var askResearch by remember { mutableStateOf(false) }
    var researchSpot by remember { mutableStateOf<com.lewisfoto.app.data.UserSpot?>(null) }

    val photoPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult
        photoUri = uri
        scope.launch {
            val gps = ExifGeo.readGps(ctx, uri)
            if (gps != null) {
                lat = gps.first; lon = gps.second
                status = "GPS-Tag gefunden: ${"%.5f".format(gps.first)}, ${"%.5f".format(gps.second)} \u2013 \u00fcberschreibt die Kartenposition."
            } else {
                status = "Foto hat keinen GPS-Tag \u2013 bitte Punkt auf der Karte w\u00e4hlen oder anderes Foto."
            }
        }
    }

    // Reverse-Geocoding + Vorschlag sobald Koordinaten stehen
    fun refreshInfo() {
        val la = lat; val lo = lon
        if (la == null || lo == null) return
        scope.launch {
            osmInfo = ReverseGeocode.describe(la, lo)
            val nearest = viewModel.allSpotsForMap()
                .minByOrNull { d2(it.lat, it.lon, la, lo) }
            suggestion = nearest?.let { "N\u00e4chster bekannter Spot: ${it.name} (${"%.1f".format(d2(it.lat, it.lon, la, lo))} km)" } ?: ""
        }
    }

    if (askResearch && researchSpot != null) {
        AlertDialog(
            onDismissRequest = { askResearch = false; onDone() },
            title = { Text("Zusatzinfos suchen?") },
            text = { Text("Soll die App im Internet nach Informationen zu dieser Location suchen (Ortsangabe, Parkm\u00f6glichkeiten, nahegelegene Highlights, beste Zeit, Sicherheitstipps)?") },
            confirmButton = {
                TextButton(onClick = {
                    val spot = researchSpot!!
                    askResearch = false
                    scope.launch {
                        saving = true
                        val r = ResearchService.research(spot.lat, spot.lon, spot.motif.labelDe, spot.suggestedSpot)
                        UserSpotStore.update(ctx, spot.copy(
                            osmInfo = r.osmInfo.ifBlank { spot.osmInfo },
                            bestTimeOfDay = r.bestTimeOfDay,
                            tips = r.tips,
                            safety = r.safety,
                            parkingName = r.parkingName,
                            parkingNote = r.parkingNote,
                            researched = true
                        ))
                        saving = false
                        onDone()
                    }
                }) { Text("Ja, suchen") }
            },
            dismissButton = {
                TextButton(onClick = { askResearch = false; onDone() }) { Text("Nein, sp\u00e4ter") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eigene Location") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zur\u00fcck") }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                if (lat != null && lon != null)
                    "Koordinaten: ${"%.5f".format(lat)}, ${"%.5f".format(lon)}  (tippen zum Anpassen auf der Karte)"
                else "Kein Punkt gew\u00e4hlt \u2013 bitte zuerst auf der Karte tippen oder Foto mit GPS-Tag laden.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (osmInfo.isNotBlank()) {
                Text("Gefundene Ortsangabe (OSM): $osmInfo", style = MaterialTheme.typography.bodySmall)
            }
            if (suggestion.isNotBlank()) {
                Text(suggestion, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Titel der Location *") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(expanded = motifExpanded, onExpandedChange = { motifExpanded = it }) {
                OutlinedTextField(
                    value = motif?.labelDe ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Motiv-Kategorie") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = motifExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(expanded = motifExpanded, onDismissRequest = { motifExpanded = false }) {
                    viewModel.motifs.filter { it != Motif.OTHER }.forEach { m ->
                        DropdownMenuItem(
                            text = { Text(m.labelDe) },
                            onClick = { motif = m; motifExpanded = false }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = desc,
                onValueChange = { desc = it },
                label = { Text("Beschreibung (optional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(onClick = {
                    photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) { Text(if (photoUri != null) "Foto gew\u00e4hlt \u2713" else "Foto mit Geo-Tag laden (optional)") }
                Text(
                    "Hinweis: Hat das Foto GPS-Geodaten, werden diese Koordinaten gespeichert \u2013 sie \u00fcberschreiben die auf der Karte gew\u00e4hlte Position.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            if (status != null) {
                Text(status!!, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    enabled = name.isNotBlank() && lat != null && lon != null && !saving,
                    onClick = {
                        saving = true
                        scope.launch {
                            val id = "user_" + UUID.randomUUID().toString().take(8)
                            val photoPath = photoUri?.let { ExifGeo.copyPhoto(ctx, it, id) }
                            val la = lat!!; val lo = lon!!
                            if (osmInfo.isBlank()) osmInfo = ReverseGeocode.describe(la, lo)
                            val spot = com.lewisfoto.app.data.UserSpot(
                                id = id,
                                name = name.trim(),
                                motif = motif ?: Motif.OTHER,
                                lat = la, lon = lo,
                                description = desc.trim(),
                                photoPath = photoPath,
                                osmInfo = osmInfo,
                                suggestedSpot = suggestion,
                                createdAt = System.currentTimeMillis()
                            )
                            UserSpotStore.add(ctx, spot)
                            saving = false
                            askResearch = true
                            researchSpot = spot
                        }
                    }
                ) { Text(if (saving) "Speichern\u2026" else "Speichern") }
                OutlinedButton(onClick = onBack) { Text("Abbrechen") }
            }
        }
    }
}

private fun d2(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val r = 6371.0
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2)
    return r * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
}
