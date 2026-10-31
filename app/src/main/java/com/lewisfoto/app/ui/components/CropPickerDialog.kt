package com.lewisfoto.app.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun CropPickerDialog(
    bitmap: Bitmap,
    onConfirm: (FloatArray) -> Unit,
    onDismiss: () -> Unit
) {
    val img = bitmap.asImageBitmap()
    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    var crop by remember { mutableStateOf(floatArrayOf(0f, 0f, 1f, 1f)) }

    val aspect = bitmap.width.toFloat() / bitmap.height.toFloat()
    val targetAspect = 9f / 16f

    fun clamp(c: FloatArray): FloatArray {
        var x = c[0].coerceIn(0f, 1f - c[2])
        var y = c[1].coerceIn(0f, 1f - c[3])
        var w = c[2].coerceIn(0.2f, 1f)
        var h = w / aspect * targetAspect / (1f / aspect)
        h = (w * (16f / 9f) / aspect).coerceIn(0.2f, 1f)
        if (y + h > 1f) y = 1f - h
        if (x + w > 1f) x = 1f - w
        return floatArrayOf(x, y, w, h)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Bildausschnitt w\u00e4hlen") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Querformat-Foto: bitte den Ausschnitt f\u00fcr den Hochformat-Hintergrund markieren (verschieben).",
                    style = MaterialTheme.typography.bodySmall
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(aspect)
                        .onSizeChanged { boxSize = it }
                        .pointerInput(Unit) {
                            detectDragGestures { change, drag ->
                                change.consume()
                                if (boxSize.width > 0) {
                                    val c = crop.copyOf()
                                    c[0] = (c[0] + drag.x / boxSize.width).coerceIn(0f, 1f - c[2])
                                    c[1] = (c[1] + drag.y / boxSize.height).coerceIn(0f, 1f - c[3])
                                    crop = clamp(c)
                                }
                            }
                        }
                ) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        drawImage(img)
                        val x = crop[0] * size.width
                        val y = crop[1] * size.height
                        val w = crop[2] * size.width
                        val h = crop[3] * size.height
                        drawRect(Color(0x80FFFFFF), Offset(x, y), Size(w, h))
                        drawRect(Color.White, Offset(x, y), Size(w, h), style = androidx.compose.ui.graphics.drawscope.Stroke(2.dp.toPx()))
                        drawRect(Color(0x99000000), Offset(0f, 0f), Size(size.width, y))
                        drawRect(Color(0x99000000), Offset(0f, y + h), Size(size.width, size.height - y - h))
                        drawRect(Color(0x99000000), Offset(0f, y), Size(x, h))
                        drawRect(Color(0x99000000), Offset(x + w, y), Size(size.width - x - w, h))
                    }
                }
                Text(
                    "Der wei\u00dfe Rahmen ist der gew\u00e4hlte Ausschnitt (9:16).",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = { TextButton(onClick = { onConfirm(crop) }) { Text("\u00dcbernehmen") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Abbrechen") } }
    )
}
