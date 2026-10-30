package com.lewisfoto.app.ui.home

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lewisfoto.app.data.Motif
import com.lewisfoto.app.ui.components.MotifIcon
import com.lewisfoto.app.viewmodel.AppViewModel
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(132.dp)
                    .background(
                        Brush.verticalGradient(listOf(Color(0xFF0B3D4A), Color(0xFF2E8CA3)))
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Column {
                    Text(
                        text = "Natur- & Wildlife-Fotografie",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Lewis & Harris, Äußere Hebriden",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFDCEFF3),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable { showDatePicker = true }
                            .background(
                                Color(0x33FFFFFF),
                                RoundedCornerShape(999.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Icon(
                            Icons.Filled.CalendarMonth,
                            contentDescription = "Reisedatum",
                            tint = Color.White
                        )
                        Text(
                            text = " Reise: ${tripDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN))} · ${season.labelDe}",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Text(
                    text = "Saisonhinweise für dein Reisedatum",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                notes.take(4).forEach { note ->
                    Text(
                        text = "• $note",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
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
                items(viewModel.motifs) { motif ->
                    MotifTile(motif = motif, onClick = { onMotifClick(motif) })
                }
            }
        }
    }
}

@Composable
private fun MotifTile(motif: Motif, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.9f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(motif.colorHex)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MotifIcon(motif = motif, size = 24)
            Text(
                text = motif.labelDe,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
