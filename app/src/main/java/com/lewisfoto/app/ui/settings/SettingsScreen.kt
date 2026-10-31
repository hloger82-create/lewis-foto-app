package com.lewisfoto.app.ui.settings

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lewisfoto.app.ui.components.CropPickerDialog
import com.lewisfoto.app.util.HomeBackground
import com.lewisfoto.app.viewmodel.AppViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: AppViewModel) {
    val darkMode by viewModel.darkMode.collectAsStateWithLifecycle()
    val ebirdKey by viewModel.ebirdApiKey.collectAsStateWithLifecycle()
    var keyText by remember { mutableStateOf(ebirdKey) }
    var keySaved by remember { mutableStateOf(false) }

    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    var pendingUri by remember { mutableStateOf<Uri?>(null) }
    var pendingBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    var showCrop by remember { mutableStateOf(false) }
    var bgMsg by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) { viewModel.reloadHomeBg() }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri == null) return@rememberLauncherForActivityResult
            scope.launch {
                val bmp = ctx.contentResolver.openInputStream(uri)?.use {
                    BitmapFactory.decodeStream(it)
                }
                if (bmp == null) {
                    bgMsg = "Foto konnte nicht geladen werden."
                    return@launch
                }
                if (bmp.height >= bmp.width) {
                    val res = HomeBackground.saveFromUri(ctx, uri)
                    bgMsg = if (res.isSuccess) "Hintergrundbild gespeichert (Hochformat \u2013 automatisch angepasst)." else "Fehler: ${res.exceptionOrNull()?.message}"
                    viewModel.reloadHomeBg()
                } else {
                    pendingUri = uri
                    pendingBitmap = bmp
                    showCrop = true
                }
            }
        }
    )

    if (showCrop && pendingBitmap != null && pendingUri != null) {
        CropPickerDialog(
            bitmap = pendingBitmap!!,
            onConfirm = { crop ->
                scope.launch {
                    val res = HomeBackground.saveCropped(ctx, pendingUri!!, crop)
                    bgMsg = if (res.isSuccess) "Bildausschnitt als Hintergrund gespeichert." else "Fehler: ${res.exceptionOrNull()?.message}"
                    showCrop = false
                    pendingUri = null
                    pendingBitmap = null
                    viewModel.reloadHomeBg()
                }
            },
            onDismiss = {
                showCrop = false
                pendingUri = null
                pendingBitmap = null
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Einstellungen") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("Darstellung", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Dunkelmodus", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text(
                        "F\u00fcr fr\u00fche Morgen- und Abendstunden",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = darkMode,
                    onCheckedChange = { viewModel.setDarkMode(it) }
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text("Hintergrundbild (Startbildschirm)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(
                "Eigenes Foto als Hintergrund w\u00e4hlen. Hochkantfotos werden automatisch angepasst; bei Querformat w\u00e4hlst du den Ausschnitt.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
            Row(modifier = Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(onClick = {
                    photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) {
                    Text("Foto ausw\u00e4hlen")
                }
                if (viewModel.hasCustomHomeBg()) {
                    OutlinedButton(
                        onClick = { scope.launch { viewModel.clearHomeBg(); bgMsg = "Standard-Hintergrund wiederhergestellt." } },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Zur\u00fccksetzen")
                    }
                }
            }
            if (bgMsg != null) {
                Text(bgMsg!!, color = Color(0xFF2E7D32), style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 6.dp))
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text("eBird-API-Key", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(
                text = "Optional, f\u00fcr \u201eSichtungen\u201c (letzte 3 Tage, 50 km Umkreis). Kostenlos beantragen unter https://ebird.org/api/keygen",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
            OutlinedTextField(
                value = keyText,
                onValueChange = { keyText = it; keySaved = false },
                label = { Text("eBird API Key") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
            )
            Row(modifier = Modifier.padding(top = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(onClick = { viewModel.setEbirdApiKey(keyText); keySaved = true }) {
                    Text("Speichern")
                }
                if (keySaved) {
                    Text(" gespeichert \u2713", color = Color(0xFF2E7D32), style = MaterialTheme.typography.bodySmall)
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text("Offline-Karten", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(
                text = "Kartendaten werden lokal zwischengespeichert (Offline-Nutzung auf den Inseln m\u00f6glich, wenn Kacheln zuvor geladen wurden).",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
