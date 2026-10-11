package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.PoliceGold
import com.example.ui.theme.PoliceNavyPrimary

// Brand Colors
val WhatsAppGreen = Color(0xFF25D366)
val ImoBlue = Color(0xFF0088CC)

@Composable
fun ContactCard(
    name: String,
    designationOrWard: String,
    mobile: String,
    addressOrVillages: String = "",
    photoUrl: String = "",
    email: String = "",
    mapsUrl: String = "",
    isFavorite: Boolean = false,
    onFavoriteToggle: () -> Unit = {}
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Profile Avatar Image
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(PoliceNavyPrimary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    if (photoUrl.isNotBlank()) {
                        AsyncImage(
                            model = photoUrl,
                            contentDescription = name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            tint = PoliceNavyPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = designationOrWard,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )

                    if (addressOrVillages.isNotBlank()) {
                        Text(
                            text = addressOrVillages,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Phone",
                            tint = EmeraldGreen,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = mobile,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            ),
                            color = EmeraldGreen
                        )
                    }
                }

                // Favorite Toggle Button
                IconButton(onClick = onFavoriteToggle) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) EmergencyRed else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons Row (Call, WhatsApp, imo, Maps)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Call Action Button
                Button(
                    onClick = { launchDialer(context, mobile) },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call",
                        modifier = Modifier.size(15.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("কল", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                // WhatsApp Button with Green WhatsApp Badge Logo
                Button(
                    onClick = { launchWhatsApp(context, mobile) },
                    colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.weight(1.3f)
                ) {
                    Surface(
                        color = Color.White,
                        shape = CircleShape,
                        modifier = Modifier.size(18.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = "WhatsApp Logo",
                                modifier = Modifier.size(12.dp),
                                tint = WhatsAppGreen
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("হোয়াটসঅ্যাপ", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                // IMO Button with Blue IMO Badge Logo
                Button(
                    onClick = { launchImo(context, mobile) },
                    colors = ButtonDefaults.buttonColors(containerColor = ImoBlue),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.weight(1.1f)
                ) {
                    Surface(
                        color = Color.White,
                        shape = CircleShape,
                        modifier = Modifier.size(18.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Forum,
                                contentDescription = "IMO Logo",
                                modifier = Modifier.size(12.dp),
                                tint = ImoBlue
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("ইমো", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                if (mapsUrl.isNotBlank()) {
                    IconButton(
                        onClick = { launchMaps(context, mapsUrl) },
                        modifier = Modifier
                            .size(36.dp)
                            .background(PoliceGold.copy(alpha = 0.15f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Map",
                            tint = PoliceGold,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

fun launchDialer(context: Context, phoneNumber: String) {
    runCatching {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        context.startActivity(intent)
    }
}

fun launchWhatsApp(context: Context, phoneNumber: String) {
    runCatching {
        val cleanNumber = phoneNumber.replace("+", "").replace(" ", "").replace("-", "").trim()
        val formattedNumber = if (cleanNumber.startsWith("0")) "88$cleanNumber" else cleanNumber
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$formattedNumber"))
        context.startActivity(intent)
    }
}

fun launchImo(context: Context, phoneNumber: String) {
    runCatching {
        val cleanNumber = phoneNumber.replace("+", "").replace(" ", "").replace("-", "").trim()
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanNumber"))
        context.startActivity(intent)
    }
}

fun launchMaps(context: Context, mapsUrl: String) {
    runCatching {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
        context.startActivity(intent)
    }
}
