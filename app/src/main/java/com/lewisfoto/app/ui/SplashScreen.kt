package com.lewisfoto.app.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onDone: () -> Unit) {
    val transition = rememberInfiniteTransition(label = "splash")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2600, easing = LinearEasing)),
        label = "progress"
    )

    LaunchedEffect(Unit) {
        delay(2200)
        onDone()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B3D4A)),
        contentAlignment = Alignment.Center
    ) {
        val logoSize = 200.dp
        Box(modifier = Modifier.size(logoSize + 24.dp), contentAlignment = Alignment.Center) {
            // Ladebalken: Kreisbogen um das Logo herum
            Canvas(modifier = Modifier.matchParentSize()) {
                val stroke = 6.dp.toPx()
                val inset = stroke / 2f
                val sweep = 360f * progress
                drawArc(
                    color = Color(0xFFE8DCC3),
                    startAngle = -90f,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = androidx.compose.ui.geometry.Offset(inset, inset),
                    size = androidx.compose.ui.geometry.Size(size.width - 2 * inset, size.height - 2 * inset),
                    style = Stroke(width = stroke, cap = androidx.compose.ui.graphics.StrokeCap.Round)
                )
            }
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(com.lewisfoto.app.R.drawable.logo_splash),
                contentDescription = "Lewis & Harris Logo",
                modifier = Modifier.size(logoSize)
            )
        }
        Text(
            text = "Lewis & Harris",
            color = Color(0xFFE8DCC3),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        )
    }
}
