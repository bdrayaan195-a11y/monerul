package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.PoliceGold
import com.example.ui.theme.PoliceNavyDark
import com.example.ui.theme.PoliceNavyPrimary
import com.example.ui.viewmodel.ScreenRoute
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }

    val scaleAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1.05f else 0.85f,
        animationSpec = repeatable(
            iterations = Int.MAX_VALUE,
            animation = tween(1200, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseScale"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2200)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PoliceNavyDark, PoliceNavyPrimary, PoliceNavyDark)
                )
            )
            .clickable { onSplashFinished() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Animated Logo Badge
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .scale(scaleAnim)
                    .clip(CircleShape)
                    .background(PoliceGold.copy(alpha = 0.15f))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(PoliceNavyPrimary)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_khilpara_police_logo_1785418299126),
                        contentDescription = "Logo Emblem",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                ),
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "খিলপাড়া • রামনারায়ণপুর • নোয়াখোলা ইউনিয়ন",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 14.sp
                ),
                color = PoliceGold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "চাটখিল, নোয়াখালী • বাংলাদেশ পুলিশ",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp
                ),
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            CircularProgressIndicator(
                color = PoliceGold,
                strokeWidth = 3.dp,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = EmergencyRed,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "জরুরি হটলাইন: ৯৯৯",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }
        }

        // Version Info at Bottom
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "সংস্করণ v1.0.0 (প্রোডাকশন)",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.5f)
            )
        }
    }
}
