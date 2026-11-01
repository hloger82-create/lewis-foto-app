package com.lewisfoto.app.util

import android.content.Context
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

object ExifGeo {

    suspend fun readGps(context: Context, uri: Uri): Pair<Double, Double>? = withContext(Dispatchers.IO) {
        runCatching {
            val tmp = File.createTempFile("exif", ".jpg", context.cacheDir)
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(tmp).use { out -> input.copyTo(out) }
            } ?: return@runCatching null
            val exif = ExifInterface(tmp.absolutePath)
            val out = FloatArray(2)
            val ok = exif.getLatLong(out)
            tmp.delete()
            if (ok) out[0].toDouble() to out[1].toDouble() else null
        }.getOrNull()
    }

    suspend fun copyPhoto(context: Context, uri: Uri, id: String): String? = withContext(Dispatchers.IO) {
        runCatching {
            val target = File(context.filesDir, "userphoto_$id.jpg")
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(target).use { out -> input.copyTo(out) }
            } ?: return@runCatching null
            target.absolutePath
        }.getOrNull()
    }
}
