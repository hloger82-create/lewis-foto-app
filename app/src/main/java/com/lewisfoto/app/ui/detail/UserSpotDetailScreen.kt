package com.lewisfoto.app.ui.detail

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lewisfoto.app.data.UserSpot
import com.lewisfoto.app.ui.components.MotifDot
import com.lewisfoto.app.util.UserSpotStore
import com.lewisfoto.app.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSpotDetailScreen(
    viewModel: AppViewModel,
    spot: UserSpot,
    onBack: () -> Unit
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    var showDelete by remember { mutableStateOf(false) }

    if (showDelete) {
        AlertDialog(
            onDismissRequest = { showDelete = false },
            title = { Text("Location l\u00f6schen?") },
            text = { Text("\u201e${spot.name}\u201c wird dauerhaft entfernt.") },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        spot.photoPath?.let { File(it).delete() }
                        UserSpotStore.remove(ctx, spot.id)
                        onBack()
                    }
                }) { Text("L\u00f6schen") }
            },
            dismissButton = { TextButton(onClick = { showDelete = false }) { Text("Abbrechen") } }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(spot.name, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zur\u00fcck") }
                },
                actions = {
                    IconButton(onClick = { showDelete = true }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Location l\u00f6schen", tint = Color.White)
                    }
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MotifDot(motif = spot.motif, size = 14)
                Text(spot.motif.labelDe, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Text("\u00b7 eigene Location", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            DetailCard(title = "Koordinaten & Route") {
                Text("${"%.5f".format(spot.lat)}, ${"%.5f".format(spot.lon)}", style = MaterialTheme.typography.bodyMedium)
                if (spot.osmInfo.isNotBlank()) Text("Ortsangabe (OSM): ${spot.osmInfo}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                if (spot.suggestedSpot.isNotBlank()) Text(spot.suggestedSpot, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }

            if (spot.description.isNotBlank()) {
                DetailCard(title = "Beschreibung") { Text(spot.description, style = MaterialTheme.typography.bodyMedium) }
            }

            DetailCard(title = "Beste Zeit") {
                Text(spot.bestTimeOfDay.ifBlank { "Keine Angabe \u2013 motivabh\u00e4ngig w\u00e4hlen (z. B. D\u00e4mmerung)." }, style = MaterialTheme.typography.bodyMedium)
            }

            DetailCard(title = "Tipps zur Location") {
                if (spot.tips.isEmpty()) Text("Noch keine Tipps \u2013 wurden nicht recherchiert.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                else spot.tips.forEach { Text("\u2022 $it", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 2.dp)) }
            }

            DetailCard(title = "Sicherheit & Ethik") {
                if (spot.safety.isEmpty()) Text("Allgemein g\u00fcltig: Abstand zu Wildtieren, keine Nester st\u00f6ren, Hunde an die Leine.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                else spot.safety.forEach { Text("\u2022 $it", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 2.dp)) }
            }

            DetailCard(title = "Parkm\u00f6glichkeit") {
                if (spot.parkingName.isNotBlank()) {
                    Text(spot.parkingName, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                    if (spot.parkingNote.isNotBlank()) Text(spot.parkingNote, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else Text("Keine Parkm\u00f6glichkeit recherchiert \u2013 Stra\u00dfenrand/Ausweichbucht pr\u00fcfen.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            OutlinedButton(onClick = { showDelete = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Location l\u00f6schen")
            }
        }
    }
}

@Composable
private fun DetailCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            content()
        }
    }
}
