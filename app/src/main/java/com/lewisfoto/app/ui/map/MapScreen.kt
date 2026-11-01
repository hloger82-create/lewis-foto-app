package com.lewisfoto.app.ui.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lewisfoto.app.data.FotoSpot
import com.lewisfoto.app.data.Motif
import com.lewisfoto.app.ui.components.MotifDot
import com.lewisfoto.app.ui.components.ProbabilityBadge
import com.lewisfoto.app.util.WeatherService
import com.lewisfoto.app.viewmodel.AppViewModel
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.MapTileIndex
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.TilesOverlay

private class RainViewerTileSource(private val host: String, private val path: String) : OnlineTileSourceBase(
    "RainViewer", 0, 7, 256, ".png", arrayOf(host)
) {
    override fun getTileURLString(pMapTileIndex: Long): String {
        val z = MapTileIndex.getZoom(pMapTileIndex)
        val x = MapTileIndex.getX(pMapTileIndex)
        val y = MapTileIndex.getY(pMapTileIndex)
        return "$host$path/256/$z/$x/$y/2/1_1.png"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: AppViewModel,
    motif: Motif?,
    onSpotClick: (FotoSpot) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val tripSeason by viewModel.season.collectAsStateWithLifecycle()
    val customCoords by viewModel.customCoords.collectAsStateWithLifecycle()
    var activeFilter by remember { mutableStateOf(motif) }
    var radarOn by remember { mutableStateOf(false) }
    var zoomBig by remember { mutableStateOf(false) }
    var radarFrames by remember { mutableStateOf<Pair<String, List<String>>?>(null) }

    LaunchedEffect(radarOn) {
        if (radarOn && radarFrames == null) {
            WeatherService.rainViewerFrames().onSuccess { radarFrames = it }
        }
    }

    val allSpots = remember { viewModel.motifs.flatMap { viewModel.spotsForMotif(it) } }
    val visibleSpots = remember(activeFilter) {
        if (activeFilter == null) allSpots else allSpots.filter { it.motif == activeFilter }
    }

    val mapView = remember {
        MapView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            minZoomLevel = 7.0
            maxZoomLevel = 18.0
            controller.setZoom(8.5)
            controller.setCenter(GeoPoint(57.98, -6.75))
            val zoomListener = object : MapListener {
                override fun onScroll(event: ScrollEvent?): Boolean = false
                override fun onZoom(event: ZoomEvent?): Boolean {
                    zoomBig = zoomLevelDouble >= 14.0
                    return false
                }
            }
            addMapListener(zoomListener)
            zoomBig = zoomLevelDouble >= 14.0
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    DisposableEffect(radarOn, radarFrames) {
        val frames = radarFrames
        if (radarOn && frames != null && frames.second.isNotEmpty()) {
            val (host, paths) = frames
            val path = paths.last()
            val tileSource = RainViewerTileSource(host, path)
            val provider = MapTileProviderBasic(context)
            provider.setTileSource(tileSource)
            val overlay = TilesOverlay(provider, context)
            mapView.overlayManager.add(overlay)
            mapView.invalidate()
            onDispose { mapView.overlayManager.remove(overlay) }
        } else {
            onDispose { }
        }
    }

    DisposableEffect(visibleSpots, customCoords, zoomBig) {
        mapView.overlays.removeAll { it is Marker }
        visibleSpots.forEach { spot ->
            val marker = Marker(mapView).apply {
                val (la, lo) = viewModel.coordsOf(spot)
                position = GeoPoint(la, lo)
                title = spot.name
                val rating = spot.ratingFor(tripSeason)
                snippet = "${spot.motif.labelDe} · ${rating?.probability ?: 0} %"
                icon = coloredPin(context, spot.motif, if (zoomBig) 2f else 1f)
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                setOnMarkerClickListener { _, _ ->
                    onSpotClick(spot)
                    true
                }
            }
            mapView.overlays.add(marker)
        }
        mapView.invalidate()
        onDispose { mapView.overlays.removeAll { it is Marker } }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(56.dp),
                title = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            if (activeFilter != null) "Karte · ${activeFilter!!.labelDe}" else "Fotospots-Karte",
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                },
                actions = {
                    IconToggleButton(checked = radarOn, onCheckedChange = { radarOn = it }) {
                        Icon(
                            imageVector = if (radarOn) Icons.Filled.Cloud else Icons.Outlined.CloudOff,
                            contentDescription = if (radarOn) "Radar an" else "Radar aus",
                            tint = Color.White
                        )
                    }
                },
                navigationIcon = {
                    if (motif != null) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = activeFilter == null,
                        onClick = { activeFilter = null },
                        label = { Text("Alle", maxLines = 1) }
                    )
                    viewModel.motifs.forEach { m ->
                        FilterChip(
                            selected = activeFilter == m,
                            onClick = { activeFilter = if (activeFilter == m) null else m },
                            label = { Text(m.labelDe, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                            leadingIcon = {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(Color(m.colorHex))
                                        .padding(5.dp)
                                ) {}
                            }
                        )
                    }
                }
                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AndroidView(factory = { mapView }, modifier = Modifier.fillMaxSize())
            }

            if (visibleSpots.isEmpty()) {
                Text(
                    text = "Keine Spots für diesen Filter.",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.45f)
                ) {
                    items(visibleSpots, key = { it.id }) { spot ->
                        SpotListItem(
                            spot = spot,
                            season = tripSeason,
                            onClick = { onSpotClick(spot) }
                        )
                    }
                }
            }
        }
    }
}

private fun coloredPin(context: android.content.Context, motif: Motif, scale: Float = 1f): Drawable? {
    val density = context.resources.displayMetrics.density
    val sizePx = (40 * scale * density).toInt()
    val bmp = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val cx = sizePx / 2f
    val headR = sizePx * 0.30f
    val headCy = sizePx * 0.32f
    val tipY = sizePx * 0.97f
    val motifColor = Color(motif.colorHex).toArgb()
    paint.color = motifColor
    val pinPath = Path().apply {
        moveTo(cx - headR, headCy)
        arcTo(RectF(cx - headR, headCy - headR, cx + headR, headCy + headR), 180f, 180f)
        lineTo(cx, tipY)
        close()
    }
    canvas.drawPath(pinPath, paint)
    paint.color = android.graphics.Color.WHITE
    canvas.drawCircle(cx, headCy, headR * 0.72f, paint)
    val icon = ContextCompat.getDrawable(context, motif.icon)?.mutate() ?: return null
    val iconSize = (headR * 1.15f).toInt()
    icon.setBounds(
        (cx - iconSize / 2).toInt(), (headCy - iconSize / 2).toInt(),
        (cx + iconSize / 2).toInt(), (headCy + iconSize / 2).toInt()
    )
    icon.setTint(motifColor)
    icon.draw(canvas)
    return BitmapDrawable(context.resources, bmp)
}

@Composable
private fun SpotListItem(
    spot: FotoSpot,
    season: com.lewisfoto.app.data.Season,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MotifDot(motif = spot.motif, size = 12)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = spot.name,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${spot.motif.labelDe} · ${spot.parking.name}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            ProbabilityBadge(spot.ratingFor(season)?.probability ?: 0)
        }
    }
}
