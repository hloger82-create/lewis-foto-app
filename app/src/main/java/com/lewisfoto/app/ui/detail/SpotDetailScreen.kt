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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowDown
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.SafetyDivider
import androidx.compose.material.icons.filled.Tide
import androidx.compose.material.icons.filled.TipsAndUpdates
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lewisfoto.app.data.FotoSpot
import com.lewisfoto.app.ui.components.MotifDot
import com.lewisfoto.app.ui.components.ProbabilityBadge
import com.lewisfoto.app.ui.components.RatingBars
import com.lewisfoto.app.util.GeoUtils
import com.lewisfoto.app.viewmodel.AppViewModel

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(spot.name) },
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
                    .height(120.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF0B3D4A), Color(spot.motif.colorHex))
                        )
                    )
                    .padding(18.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        MotifDot(motif = spot.motif, size = 14)
                        Text(
                            text = "  ${spot.motif.labelDe}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 6.dp)
                    ) {
                        ProbabilityBadge(rating?.probability ?: 0)
                        Text(
                            text = "  Saison: ${season.labelDe}",
                            color = Color(0xFFE8F4F6),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (rating != null) {
                    ExpandableSectionCard(
                        title = "Häufigkeit & Wahrscheinlichkeit (${season.labelDe})",
                        expandedByDefault = true
                    ) {
                        RatingBars(frequency = rating.frequency, probability = rating.probability)
                    }
                }

                ExpandableSectionCard(title = "Beste Zeit", icon = Icons.Filled.LightMode) {
                    Text(spot.bestTimeOfDay, style = MaterialTheme.typography.bodyMedium)
                }

                if (spot.tide != null) {
                    ExpandableSectionCard(title = "Gezeiten", icon = Icons.Filled.Tide) {
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
                    }
                }

                ExpandableSectionCard(title = "Tipps zur Location", icon = Icons.Filled.TipsAndUpdates) {
                    spot.tips.forEach { tip ->
                        Text(
                            text = "• $tip",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                }

                ExpandableSectionCard(title = "Sicherheit & Ethik", icon = Icons.Filled.SafetyDivider) {
                    spot.safety.forEach { s ->
                        Text(
                            text = "• $s",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                }

                ExpandableSectionCard(title = "Anfahrt", icon = Icons.Filled.Place) {
                    Text(
                        spot.arrival.fromStornoway,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        spot.arrival.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                ExpandableSectionCard(title = "Parkmöglichkeit", icon = Icons.Filled.DirectionsCar) {
                    Text(spot.parking.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                    Text(
                        spot.parking.note,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                ExpandableSectionCard(title = "Koordinaten & Route", icon = Icons.Filled.LocationOn) {
                    Text("${spot.lat}, ${spot.lon}", style = MaterialTheme.typography.bodyMedium)
                    if (spot.what3words != null) {
                        Text(
                            "what3words: ${spot.what3words}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    OutlinedButton(
                        onClick = { GeoUtils.openNavigation(context, spot.lat, spot.lon, spot.name) },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Route in Google Maps planen")
                    }
                }

                ExpandableSectionCard(title = "Datenquellen") {
                    spot.sources.forEach { source ->
                        Text("• $source", style = MaterialTheme.typography.bodySmall)
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
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(16.dp),
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
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.AutoMirrored.Filled.KeyboardArrowUp else Icons.AutoMirrored.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Einklappen" else "Ausklappen",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            AnimatedVisibility(visible = expanded) {
                Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                    content()
                }
            }
        }
    }
}
