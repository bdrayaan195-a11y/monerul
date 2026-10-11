package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.PoliceGold
import com.example.ui.theme.PoliceNavyPrimary
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.ScreenRoute

data class CategoryCardItem(
    val id: String,
    val titleBn: String,
    val titleEn: String,
    val icon: ImageVector,
    val color: Color,
    val route: ScreenRoute,
    val unionId: String? = null
)

@Composable
fun HomeScreen(
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val notices by viewModel.notices.collectAsState()
    val hotlineNumbers by viewModel.hotlineNumbers.collectAsState()
    val adminLoggedIn by viewModel.adminLoggedIn.collectAsState()
    val scrollState = rememberScrollState()

    val categoryList = listOf(
        CategoryCardItem("1", "১. পুলিশ তদন্ত কেন্দ্র", "Police Officers", Icons.Default.LocalPolice, PoliceNavyPrimary, ScreenRoute.POLICE_OFFICERS),
        CategoryCardItem("2", "২. খিলপাড়া ইউনিয়ন", "Khilpara Union", Icons.Default.LocationCity, Color(0xFF0284C7), ScreenRoute.UNION_DETAILS, "khilpara"),
        CategoryCardItem("3", "৩. রামনারায়ণপুর ইউনিয়ন", "Ramnarayanpur Union", Icons.Default.AccountBalance, EmeraldGreen, ScreenRoute.UNION_DETAILS, "ramnarayanpur"),
        CategoryCardItem("4", "৪. নোয়াখোলা ইউনিয়ন", "Noakhola Union", Icons.Default.Apartment, PoliceGold, ScreenRoute.UNION_DETAILS, "noakhola"),
        CategoryCardItem("5", "৫. সরকারি স্কুল ও কলেজ", "Schools & Colleges", Icons.Default.School, Color(0xFF7C3AED), ScreenRoute.SCHOOL_COLLEGE),
        CategoryCardItem("6", "৬. CNG স্টেশন ও লাইনম্যান", "CNG Stations & Linemen", Icons.Default.DirectionsCar, Color(0xFFD97706), ScreenRoute.CNG_STATION),
        CategoryCardItem("7", "৭. গুরুত্বপূর্ণ ব্যক্তিবর্গ", "Important Persons", Icons.Default.Groups, Color(0xFF059669), ScreenRoute.IMPORTANT_PERSONS)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        // Hero Banner Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_union_banner_1785418313982),
                contentDescription = "Union & Police Hero Banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, PoliceNavyPrimary.copy(alpha = 0.85f))
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Surface(
                    color = PoliceGold,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "চাটখিল থানা, নোয়াখালী",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = PoliceNavyPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp
                    ),
                    color = Color.White
                )
                Text(
                    text = "আইন-শৃঙ্খলা রক্ষা ও ৩টি ইউনিয়নের নাগরিক ডিরেক্টরি",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }

        // Quick Search Shortcut Bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clickable { viewModel.navigateTo(ScreenRoute.SEARCH) },
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "নাম, গ্রাম, ওয়ার্ড, স্কুল বা ফোন নম্বর খুঁজুন...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Emergency Hotline Card Ticker
        val latestEmergency = notices.firstOrNull { it.isEmergency } ?: notices.firstOrNull()
        if (latestEmergency != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clickable { viewModel.navigateTo(ScreenRoute.NOTICES) },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = EmergencyRed.copy(alpha = 0.1f)),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(EmergencyRed, EmergencyRed)))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Alert",
                        tint = EmergencyRed,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "জরুরি পুলিশ বিজ্ঞপ্তি",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = EmergencyRed
                        )
                        Text(
                            text = latestEmergency.title,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Go",
                        tint = EmergencyRed
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Categories Grid Header
        PaddingValues(horizontal = 16.dp, vertical = 4.dp).let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "প্রধান ক্যাটাগরি সমূহ",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "সব তথ্য দেখুন",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { viewModel.navigateTo(ScreenRoute.SEARCH) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 6 Main Categories Grid
        Column(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            for (i in categoryList.indices step 2) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val item1 = categoryList[i]
                    CategoryGridCard(
                        item = item1,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            if (item1.unionId != null) {
                                viewModel.selectUnion(item1.unionId)
                            } else {
                                viewModel.navigateTo(item1.route)
                            }
                        }
                    )

                    if (i + 1 < categoryList.size) {
                        val item2 = categoryList[i + 1]
                        CategoryGridCard(
                            item = item2,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                if (item2.unionId != null) {
                                    viewModel.selectUnion(item2.unionId)
                                } else {
                                    viewModel.navigateTo(item2.route)
                                }
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Hotline Action Bar (Police Station Incharge, Fire Service, Ambulance, 999)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PoliceNavyPrimary)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalPolice,
                            contentDescription = "Hotline",
                            tint = PoliceGold,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "জরুরি হটলাইন নম্বরসমূহ",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }

                    if (adminLoggedIn) {
                        Surface(
                            color = PoliceGold,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.clickable { viewModel.navigateTo(ScreenRoute.ADMIN_PANEL) }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Hotlines",
                                    tint = PoliceNavyPrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "এডিট করুন",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PoliceNavyPrimary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                val hotlines = hotlineNumbers ?: com.example.data.local.InitialData.defaultHotlineNumbers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    QuickCallChip(hotlines.title1, hotlines.number1, context)
                    QuickCallChip(hotlines.title2, hotlines.number2, context)
                    QuickCallChip(hotlines.title3, hotlines.number3, context)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun CategoryGridCard(
    item: CategoryCardItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(115.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(item.color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.titleBn,
                    tint = item.color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.titleBn,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                ),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

@Composable
fun QuickCallChip(
    title: String,
    mobile: String,
    context: android.content.Context
) {
    Surface(
        color = Color.White.copy(alpha = 0.15f),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.clickable {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$mobile"))
            context.startActivity(intent)
        }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.labelSmall, color = PoliceGold, fontSize = 10.sp)
            Text(mobile, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.White, fontSize = 11.sp)
        }
    }
}
