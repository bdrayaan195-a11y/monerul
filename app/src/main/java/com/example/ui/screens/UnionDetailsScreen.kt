package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.components.ContactCard
import com.example.ui.components.launchDialer
import com.example.ui.components.launchMaps
import com.example.ui.components.launchWhatsApp
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.PoliceGold
import com.example.ui.theme.PoliceNavyPrimary
import com.example.ui.viewmodel.MainViewModel

@Composable
fun UnionDetailsScreen(
    viewModel: MainViewModel
) {
    val unions by viewModel.unions.collectAsState()
    val selectedUnionId by viewModel.selectedUnionId.collectAsState()
    val chairman by viewModel.currentChairman.collectAsState()
    val members by viewModel.currentMembers.collectAsState()
    val villagePoliceList by viewModel.currentVillagePolice.collectAsState()
    val favorites by viewModel.favorites.collectAsState()

    val activeUnion = unions.find { it.id == selectedUnionId } ?: unions.firstOrNull()

    var selectedTab by remember { mutableStateOf(0) } // 0: চেয়ারম্যান & ইতিহাস, 1: ইউপি মেম্বারবৃন্দ, 2: গ্রাম পুলিশ (চৌকিদার)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Union Switcher Tab Bar
        if (unions.isNotEmpty()) {
            val rawIndex = unions.indexOfFirst { it.id == selectedUnionId }
            val safeIndex = if (rawIndex >= 0) rawIndex else 0

            ScrollableTabRow(
                selectedTabIndex = safeIndex.coerceIn(0, unions.size - 1),
                edgePadding = 12.dp,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                unions.forEach { union ->
                    Tab(
                        selected = union.id == selectedUnionId,
                        onClick = { viewModel.selectUnion(union.id) },
                        text = {
                            Text(
                                text = union.nameBn,
                                fontWeight = if (union.id == selectedUnionId) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                }
            }
        }

        // Sub Navigation Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                Text("চেয়ারম্যান", modifier = Modifier.padding(10.dp), fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                Text("মেম্বারবৃন্দ (${members.size})", modifier = Modifier.padding(10.dp), fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
            Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }) {
                Text("গ্রাম পুলিশ (${villagePoliceList.size})", modifier = Modifier.padding(10.dp), fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(14.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Tab 0: Chairman & Union Profile
            if (selectedTab == 0) {
                if (activeUnion != null) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = PoliceNavyPrimary)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = activeUnion.nameBn,
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    text = activeUnion.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Text("ওয়ার্ড: ${activeUnion.totalWards}টি", color = PoliceGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    Text("গ্রাম: ${activeUnion.totalVillages}টি", color = PoliceGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }

                if (chairman != null) {
                    item {
                        Text(
                            text = "ইউনিয়ন পরিষদ চেয়ারম্যান প্রোফাইল",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(vertical = 6.dp)
                        )

                        ContactCard(
                            name = chairman!!.name,
                            designationOrWard = "ইউনিয়ন পরিষদ চেয়ারম্যান (${activeUnion?.nameBn ?: ""})",
                            mobile = chairman!!.mobile,
                            addressOrVillages = chairman!!.officeAddress,
                            photoUrl = chairman!!.photoUrl,
                            email = chairman!!.email,
                            mapsUrl = chairman!!.mapsUrl,
                            isFavorite = favorites.any { it.id == "chairman_${chairman!!.id}" },
                            onFavoriteToggle = {
                                viewModel.toggleFavorite(
                                    id = "chairman_${chairman!!.id}",
                                    name = chairman!!.name,
                                    designation = "ইউপি চেয়ারম্যান",
                                    mobile = chairman!!.mobile,
                                    category = "চেয়ারম্যান",
                                    photoUrl = chairman!!.photoUrl
                                )
                            }
                        )
                    }
                }
            }

            // Tab 1: UP Members List by Ward
            if (selectedTab == 1) {
                item {
                    Text(
                        text = "ওয়ার্ডভিত্তিক ইউপি মেম্বার তালিকা (১-৯ নং ওয়ার্ড)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(members) { member ->
                    val isFav = favorites.any { it.id == "member_${member.id}" }
                    ContactCard(
                        name = member.name,
                        designationOrWard = "ইউপি মেম্বার - ওয়ার্ড নং ${member.wardNumber}",
                        mobile = member.mobile,
                        addressOrVillages = "গ্রামসমূহ: ${member.villages}",
                        photoUrl = member.photoUrl,
                        isFavorite = isFav,
                        onFavoriteToggle = {
                            viewModel.toggleFavorite(
                                id = "member_${member.id}",
                                name = member.name,
                                designation = "ইউপি মেম্বার - ওয়ার্ড ${member.wardNumber}",
                                mobile = member.mobile,
                                category = "ইউপি মেম্বার",
                                photoUrl = member.photoUrl
                            )
                        }
                    )
                }
            }

            // Tab 2: Village Police (চৌকিদার বাহিনী)
            if (selectedTab == 2) {
                item {
                    Text(
                        text = "গ্রাম পুলিশ (চৌকিদার) ও দফেদার বাহিনী",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(villagePoliceList) { police ->
                    val isFav = favorites.any { it.id == "police_${police.id}" }
                    ContactCard(
                        name = police.name,
                        designationOrWard = "গ্রাম পুলিশ / চৌকিদার",
                        mobile = police.mobile,
                        addressOrVillages = "দায়িত্বপ্রাপ্ত গ্রাম: ${police.responsibleVillages}",
                        photoUrl = police.photoUrl,
                        isFavorite = isFav,
                        onFavoriteToggle = {
                            viewModel.toggleFavorite(
                                id = "police_${police.id}",
                                name = police.name,
                                designation = "গ্রাম পুলিশ",
                                mobile = police.mobile,
                                category = "গ্রাম পুলিশ",
                                photoUrl = police.photoUrl
                            )
                        }
                    )
                }
            }
        }
    }
}
