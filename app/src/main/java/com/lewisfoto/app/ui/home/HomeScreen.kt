package com.lewisfoto.app.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.item
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lewisfoto.app.R
import com.lewisfoto.app.data.Motif
import com.lewisfoto.app.ui.components.MotifIcon
import com.lewisfoto.app.viewmodel.AppViewModel
import androidx.compose.runtime.LaunchedEffect
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: AppViewModel,
    onMotifClick: (Motif) -> Unit
) {
    val tripDate by viewModel.tripDate.collectAsStateWithLifecycle()
    val season by viewModel.season.collectAsStateWithLifecycle()
    val notes by viewModel.seasonNotes.collectAsStateWithLifecycle()
    val homeBg by viewModel.homeBg.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { viewModel.reloadHomeBg() }
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        val state = rememberDatePickerState(
            initialSelectedDateMillis = tripDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    state.selectedDateMillis?.let {
                        viewModel.setTripDate(Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate())
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Abbrechen") } }
        ) {
            DatePicker(state = state)
        }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val bgPainter = homeBg?.let { androidx.compose.ui.graphics.painter.BitmapPainter(it.asImageBitmap()) }
                ?: painterResource(R.drawable.bg_home_photo)
            Image(
                painter = bgPainter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0x66000000),
                                Color(0x33000000),
                                Color(0x66000000)
                            )
                        )
                    )
            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0x40000000))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Natur- & Wildlife-Fotografie",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { showDatePicker = true }
                            .background(Color(0x55FFFFFF), RoundedCornerShape(999.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            Icons.Filled.CalendarMonth,
                            contentDescription = "Reisedatum ändern",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = " ${tripDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN))} · ${season.labelDe}",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Text(
                    text = "Lewis & Harris · Äußere Hebriden",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFDCEFF3),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            var notesExpanded by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0x33000000))
                    .clickable { notesExpanded = !notesExpanded }
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Saisonhinweise für dein Reisedatum",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Icon(
                        imageVector = if (notesExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (notesExpanded) "Weniger anzeigen" else "Alle anzeigen",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                val shown = if (notesExpanded) notes else notes.take(3)
                shown.forEach { note ->
                    Text(
                        text = "• $note",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFE8F0F2),
                        modifier = Modifier.padding(top = 3.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Text(
                text = "Fotomotiv wählen",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                itemsIndexed(viewModel.motifs) { index, motif ->
                    MotifTile(motif = motif, alt = (index / 2 + index) % 2 == 1, onClick = { onMotifClick(motif) })
                }
                item {
                    AddSpotTile(onClick = { onMotifClick(com.lewisfoto.app.data.Motif.OTHER) })
                }
            }
            }
        }
    }
}

private val TILE_A = Color(0x66B8CCDD)   // helles, transparentes Blaugrau
private val TILE_B = Color(0x66D6E0C0)   // helles, transparentes Gruen

@Composable
private fun MotifTile(motif: Motif, alt: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3.0f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = if (alt) TILE_B else TILE_A),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MotifIcon(motif = motif, size = 30)
            Text(
                text = motif.labelDe,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun AddSpotTile(onClick: () -> Unit) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = Color(0x66B8CCDD)
        ),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                painter = painterResource(com.lewisfoto.app.R.drawable.ic_add_pin),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = "+ Eigene Location",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
