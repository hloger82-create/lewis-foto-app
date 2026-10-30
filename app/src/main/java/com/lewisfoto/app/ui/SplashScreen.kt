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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
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

    val seaDeep = Color(0xFF0B3D4A)
    val sand = Color(0xFFE8DCC3)
    val seaLight = Color(0xFF2E8CA3)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(seaDeep),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(220.dp)) {
            val cx = size.width / 2
            val cy = size.height / 2

            val island = Path().apply {
                moveTo(cx - 34f, cy - 52f)
                cubicTo(cx - 20f, cy - 58f, cx - 6f, cy - 54f, cx + 2f, cy - 46f)
                cubicTo(cx + 14f, cy - 50f, cx + 26f, cy - 44f, cx + 30f, cy - 34f)
                cubicTo(cx + 38f, cy - 26f, cx + 34f, cy - 12f, cx + 24f, cy - 6f)
                cubicTo(cx + 28f, cy + 6f, cx + 20f, cy + 18f, cx + 10f, cy + 22f)
                cubicTo(cx + 14f, cy + 34f, cx + 6f, cy + 44f, cx - 4f, cy + 48f)
                cubicTo(cx - 16f, cy + 52f, cx - 28f, cy + 46f, cx - 32f, cy + 36f)
                cubicTo(cx - 40f, cy + 26f, cx - 38f, cy + 10f, cx - 34f, cy + 0f)
                cubicTo(cx - 42f, cy - 10f, cx - 40f, cy - 26f, cx - 34f, cy - 36f)
                cubicTo(cx - 30f, cy - 44f, cx - 38f, cy - 48f, cx - 34f, cy - 52f)
                close()
            }

            drawPath(island, color = sand.copy(alpha = 0.9f))

            drawPath(
                Path().apply {
                    moveTo(cx - 12f, cy - 26f)
                    lineTo(cx - 4f, cy - 14f)
                    lineTo(cx - 12f, cy - 2f)
                    close()
                },
                color = seaDeep.copy(alpha = 0.25f)
            )
            drawPath(
                Path().apply {
                    moveTo(cx + 6f, cy - 32f)
                    lineTo(cx + 14f, cy - 18f)
                    lineTo(cx + 4f, cy - 8f)
                    close()
                },
                color = seaDeep.copy(alpha = 0.25f)
            )

            val measure = PathMeasure()
            measure.setPath(island, false)
            val total = measure.length
            val dashPath = Path()
            measure.getSegment(0f, (total * progress).coerceAtMost(total), dashPath, true)
            drawPath(
                dashPath,
                color = seaLight,
                style = Stroke(width = 5f, cap = androidx.compose.ui.graphics.StrokeCap.Round)
            )
        }
        Text(
            text = "Lewis & Harris Fotospots",
            color = sand,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        )
    }
}
