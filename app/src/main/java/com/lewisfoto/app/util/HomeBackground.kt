package com.lewisfoto.app.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

object HomeBackground {

    private const val FILE_NAME = "home_bg.jpg"

    private fun targetFile(context: Context): File =
        File(context.filesDir, FILE_NAME)

    fun hasCustom(context: Context): Boolean = targetFile(context).exists()

    fun uriFor(context: Context): Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        targetFile(context)
    )

    suspend fun saveFromUri(context: Context, uri: Uri): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val src = context.contentResolver.openInputStream(uri)
                ?: error("Foto konnte nicht ge\u00f6ffnet werden")
            val bmp = src.use { BitmapFactory.decodeStream(it) }
                ?: error("Kein g\u00fcltiges Bildformat")
            val scaled = if (maxOf(bmp.width, bmp.height) > 1600) {
                val scale = 1600f / maxOf(bmp.width, bmp.height)
                Bitmap.createScaledBitmap(bmp, (bmp.width * scale).toInt(), (bmp.height * scale).toInt(), true)
            } else bmp
            FileOutputStream(targetFile(context)).use { out ->
                scaled.compress(Bitmap.CompressFormat.JPEG, 88, out)
            }
            if (scaled !== bmp) scaled.recycle()
            bmp.recycle()
        }
    }

    suspend fun saveCropped(context: Context, uri: Uri, crop: FloatArray): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val src = context.contentResolver.openInputStream(uri)
                    ?: error("Foto konnte nicht ge\u00f6ffnet werden")
                val bmp = src.use { BitmapFactory.decodeStream(it) }
                    ?: error("Kein g\u00fcltiges Bildformat")
                val x = (crop[0] * bmp.width).toInt().coerceIn(0, bmp.width - 1)
                val y = (crop[1] * bmp.height).toInt().coerceIn(0, bmp.height - 1)
                val w = (crop[2] * bmp.width).toInt().coerceAtLeast(1)
                val h = (crop[3] * bmp.height).toInt().coerceAtLeast(1)
                val cropped = Bitmap.createBitmap(bmp, x, y, w.coerceAtMost(bmp.width - x), h.coerceAtMost(bmp.height - y))
                val scaled = if (maxOf(cropped.width, cropped.height) > 1600) {
                    val scale = 1600f / maxOf(cropped.width, cropped.height)
                    Bitmap.createScaledBitmap(cropped, (cropped.width * scale).toInt(), (cropped.height * scale).toInt(), true)
                } else cropped
                FileOutputStream(targetFile(context)).use { out ->
                    scaled.compress(Bitmap.CompressFormat.JPEG, 88, out)
                }
                if (scaled !== cropped) scaled.recycle()
                cropped.recycle()
                bmp.recycle()
            }
        }

    suspend fun loadBitmap(context: Context): Bitmap? = withContext(Dispatchers.IO) {
        runCatching {
            val f = targetFile(context)
            if (!f.exists()) null else BitmapFactory.decodeFile(f.absolutePath)
        }.getOrNull()
    }

    suspend fun clear(context: Context) = withContext(Dispatchers.IO) {
        runCatching { targetFile(context).delete() }
    }
}
