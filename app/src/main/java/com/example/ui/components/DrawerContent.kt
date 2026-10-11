package com.example.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.PoliceGold
import com.example.ui.theme.PoliceNavyLight
import com.example.ui.theme.PoliceNavyPrimary
import com.example.ui.viewmodel.ScreenRoute

data class DrawerMenuItem(
    val route: ScreenRoute?,
    val titleBn: String,
    val titleEn: String,
    val icon: ImageVector,
    val badge: String? = null,
    val isShare: Boolean = false
)

@Composable
fun DrawerContent(
    currentRoute: ScreenRoute,
    onNavigate: (ScreenRoute) -> Unit,
    onCloseDrawer: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val menuItems = listOf(
        DrawerMenuItem(ScreenRoute.HOME, "হোম", "Home", Icons.Default.Home),
        DrawerMenuItem(ScreenRoute.POLICE_OFFICERS, "পুলিশ তদন্ত কেন্দ্র", "Police Center", Icons.Default.LocalPolice, badge = "২৪/৭"),
        DrawerMenuItem(ScreenRoute.SEARCH, "অনুসন্ধান", "Search", Icons.Default.Search),
        DrawerMenuItem(ScreenRoute.FAVORITES, "প্রিয় পরিচিতি", "Favorites", Icons.Default.Favorite, badge = "সংরক্ষিত"),
        DrawerMenuItem(ScreenRoute.CHAT, "সিটিজেন চ্যাট ও জরুরি বার্তা", "Citizen Chat", Icons.Default.Chat, badge = "লাইভ"),
        DrawerMenuItem(ScreenRoute.NOTICES, "বিজ্ঞপ্তি ও পুলিশ নোটিশ", "Notices", Icons.Default.Notifications),
        DrawerMenuItem(ScreenRoute.WEB_IMPORT, "ওয়েব ডেটা ইমপোর্ট", "Web Data Import", Icons.Default.CloudDownload),
        DrawerMenuItem(ScreenRoute.ADMIN_PANEL, "এডমিন প্যানেল", "Admin Panel", Icons.Default.AdminPanelSettings, badge = "সুরক্ষিত"),
        DrawerMenuItem(null, "অ্যাপ শেয়ার করুন", "Share App", Icons.Default.Share, isShare = true),
        DrawerMenuItem(ScreenRoute.SETTINGS, "সেটিংস", "Settings", Icons.Default.Settings),
        DrawerMenuItem(ScreenRoute.DEVELOPER_INFO, "ডেভেলপার তথ্য", "Developer Info", Icons.Default.Code),
        DrawerMenuItem(ScreenRoute.ABOUT_APP, "অ্যাপ সম্পর্কে", "About App", Icons.Default.Info),
        DrawerMenuItem(ScreenRoute.PRIVACY_POLICY, "গোপনীয়তা নীতি", "Privacy Policy", Icons.Default.PrivacyTip)
    )

    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.width(310.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Header Banner in Drawer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(PoliceNavyPrimary, PoliceNavyLight)
                        )
                    )
                    .padding(vertical = 28.dp, horizontal = 20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(PoliceGold.copy(alpha = 0.2f))
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "Emblem",
                            tint = PoliceGold,
                            modifier = Modifier.size(38.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "চাটখিল, নোয়াখালী • বাংলাদেশ পুলিশ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PoliceGold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        color = EmergencyRed,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "জরুরি সেবা: ৯৯৯ | পুলিশ তদন্ত কেন্দ্র",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Navigation Items
            menuItems.forEach { item ->
                val isSelected = item.route == currentRoute

                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.titleBn,
                            tint = if (isSelected) PoliceNavyPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.titleBn,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            )
                            if (item.badge != null) {
                                Surface(
                                    color = if (item.badge == "সুরক্ষিত") EmergencyRed else PoliceGold,
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text(
                                        text = item.badge,
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    },
                    selected = isSelected,
                    onClick = {
                        onCloseDrawer()
                        if (item.isShare) {
                            shareApp(context)
                        } else if (item.route != null) {
                            onNavigate(item.route)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Divider(modifier = Modifier.padding(horizontal = 20.dp))

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "সংস্করণ: v1.0.0 (প্রোডাকশন)",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "© ২০২৬ খিলপাড়া পুলিশ তদন্ত কেন্দ্র",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun shareApp(context: Context) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "খিলপাড়া পুলিশ তদন্ত কেন্দ্র ও ইউনিয়ন তথ্যাবলি অ্যাপটি ডাউনলোড করুন: https://play.google.com/store/apps/details?id=com.aistudio.khilparapolice"
        )
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(sendIntent, "অ্যাপটি শেয়ার করুন"))
}
