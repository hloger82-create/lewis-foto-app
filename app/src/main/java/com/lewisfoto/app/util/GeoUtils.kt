package com.lewisfoto.app.util

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

object GeoUtils {

    fun openNavigation(context: Context, lat: Double, lon: Double, label: String) {
        val encoded = java.net.URLEncoder.encode(label, Charsets.UTF_8.name()).replace("+", "%20")
        val uri = "geo:$lat,$lon?q=$lat,$lon($encoded)".toUri()
        val mapsIntent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage("com.google.android.apps.maps")
        }
        runCatching { context.startActivity(mapsIntent) }
            .onFailure { runCatching { context.startActivity(Intent(Intent.ACTION_VIEW, uri)) } }
    }
}
