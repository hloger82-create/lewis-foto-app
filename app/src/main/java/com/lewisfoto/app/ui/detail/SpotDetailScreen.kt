package com.lewisfoto.app.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.SafetyDivider
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.filled.Waves
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lewisfoto.app.data.FotoSpot
import com.lewisfoto.app.data.PhotoTips
import com.lewisfoto.app.ui.components.CoordPickerDialog
import com.lewisfoto.app.ui.components.MotifDot
import com.lewisfoto.app.ui.components.ProbabilityBadge
import com.lewisfoto.app.ui.components.RatingBars
import com.lewisfoto.app.util.GeoUtils
import com.lewisfoto.app.util.TideCalculator
import com.lewisfoto.app.viewmodel.AppViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotDetailScreen(
    viewModel: AppViewModel,
    spot: FotoSpot,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val season by viewModel.season.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val isFavorite = spot.id in favorites
    val rating = spot.ratingFor(season)
    val customCoords by viewModel.customCoords.collectAsStateWithLifecycle()
    val coords = customCoords[spot.id] ?: (spot.lat to spot.lon)
    var showCoordPicker by remember { mutableStateOf(false) }

    if (showCoordPicker) {
        CoordPickerDialog(
            initialLat = coords.first,
            initialLon = coords.second,
            onDismiss = { showCoordPicker = false },
            onConfirm = { la, lo ->
                viewModel.setCustomCoords(spot.id, la, lo)
                showCoordPicker = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(spot.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleFavorite(spot.id) }) {
                        Icon(
                            if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Zur Merkliste",
                            tint = if (isFavorite) Color(0xFFDC2626) else Color.White
                        )
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
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF0B3D4A), Color(spot.motif.colorHex))
                        )
                    )
                    .padding(14.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        MotifDot(motif = spot.motif, size = 12)
                        Text(
                            text = "  ${spot.motif.labelDe}",
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        ProbabilityBadge(rating?.probability ?: 0)
                        Text(
                            text = "  Saison: ${season.labelDe}",
                            color = Color(0xFFE8F4F6),
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (rating != null) {
                    ExpandableSectionCard(
                        title = "Wahrscheinlichkeit in der ${season.labelDe}-Saison",
                        expandedByDefault = true
                    ) {
                        RatingBars(probability = rating.probability)
                    }
                }

                ExpandableSectionCard(title = "Beste Zeit", icon = Icons.Filled.LightMode) {
                    Text(spot.bestTimeOfDay, style = MaterialTheme.typography.bodyMedium)
                }

                if (spot.tide != null && spot.tide!!.relevant) {
                    val nextTides = remember(spot.id) { TideCalculator.nextEvents(spot.lat, spot.lon) }
                    val tf = DateTimeFormatter.ofPattern("dd.MM. HH:mm")
                    ExpandableSectionCard(title = "Gezeiten", icon = Icons.Filled.Waves) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = if (spot.tide!!.bestAroundLowTide) "Um Niedrigwasser (Ebbe)" else "Um Hochwasser (Flut)",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                spot.tide!!.note,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "Nächste Gezeiten (Näherung, mondbasiert):",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            nextTides.forEach { ev ->
                                Text(
                                    "${if (ev.high) "🌊 Hochwasser" else "🏖 Ebbe"}  ${ev.time.format(tf)} Uhr",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (ev.high) MaterialTheme.colorScheme.primary else Color(0xFFB07C1F)
                                )
                            }
                        }
                    }
                }

                ExpandableSectionCard(title = "Tipps zur Location", icon = Icons.Filled.TipsAndUpdates) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        spot.tips.forEach { tip ->
                            Text(text = "• $tip", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                ExpandableSectionCard(title = "Foto-Technik – Profitipps", icon = Icons.Filled.PhotoCamera) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        PhotoTips.forMotif(spot.motif).forEach { tip ->
                            Text(text = "• $tip", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                ExpandableSectionCard(title = "Sicherheit & Ethik", icon = Icons.Filled.SafetyDivider) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        spot.safety.forEach { item ->
                            Text(text = "• $item", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                ExpandableSectionCard(title = "Parkmöglichkeit", icon = Icons.Filled.DirectionsCar) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(spot.parking.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        Text(
                            spot.parking.note,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (spot.parking.distanceToSpot.isNotBlank()) {
                            Text(
                                "Entfernung Parkplatz → Ziel: ${spot.parking.distanceToSpot}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                ExpandableSectionCard(title = "Koordinaten & Route", icon = Icons.Filled.LocationOn) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("${coords.first}, ${coords.second}", style = MaterialTheme.typography.bodyMedium)
                        if (customCoords.containsKey(spot.id)) {
                            Text(
                                "Angepasste Position (Original: ${spot.lat}, ${spot.lon})",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        if (spot.what3words != null) {
                            Text(
                                "what3words: ${spot.what3words}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Row(modifier = Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(onClick = { GeoUtils.openNavigation(context, coords.first, coords.second, spot.name) }) {
                                Text("Route planen")
                            }
                            OutlinedButton(onClick = { showCoordPicker = true }) {
                                Text("Anpassen")
                            }
                            if (customCoords.containsKey(spot.id)) {
                                OutlinedButton(onClick = { viewModel.resetCustomCoords(spot.id) }) {
                                    Text("Zurücksetzen")
                                }
                            }
                        }
                    }
                }

                ExpandableSectionCard(title = "Datenquellen") {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        spot.sources.forEach { source ->
                            Text("• $source", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpandableSectionCard(
    title: String,
    icon: ImageVector? = null,
    expandedByDefault: Boolean = false,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(expandedByDefault) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Einklappen" else "Ausklappen",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            AnimatedVisibility(visible = expanded) {
                Box(modifier = Modifier.padding(start = 14.dp, end = 14.dp, bottom = 14.dp)) {
                    content()
                }
            }
        }
    }
}
