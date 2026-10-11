package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.PoliceOfficerEntity
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.PoliceGold
import com.example.ui.theme.PoliceNavyDark
import com.example.ui.theme.PoliceNavyLight
import com.example.ui.theme.PoliceNavyPrimary
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoliceOfficersScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val policeOfficers by viewModel.policeOfficers.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val adminLoggedIn by viewModel.adminLoggedIn.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedRankFilter by remember { mutableStateOf("সকল") }

    val rankFilters = listOf("সকল", "ইনচার্জ (IC)", "এসআই (SI)", "এএসআই (ASI)", "ডিউটি অফিসার")

    val filteredOfficers = remember(policeOfficers, searchQuery, selectedRankFilter) {
        policeOfficers.filter { officer ->
            val matchesQuery = searchQuery.isBlank() ||
                    officer.name.contains(searchQuery, ignoreCase = true) ||
                    officer.designation.contains(searchQuery, ignoreCase = true) ||
                    officer.rank.contains(searchQuery, ignoreCase = true) ||
                    officer.dutyArea.contains(searchQuery, ignoreCase = true) ||
                    officer.mobile.contains(searchQuery)

            val matchesRank = when (selectedRankFilter) {
                "ইনচার্জ (IC)" -> officer.rank.contains("পরিদর্শক") || officer.designation.contains("ইনচার্জ")
                "এসআই (SI)" -> officer.rank.contains("এসআই") || officer.rank.contains("উপ-পরিদর্শক")
                "এএসআই (ASI)" -> officer.rank.contains("এএসআই") || officer.rank.contains("সহকারী")
                "ডিউটি অফিসার" -> officer.designation.contains("ডিউটি")
                else -> true
            }

            matchesQuery && matchesRank
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "পুলিশ তদন্ত কেন্দ্র",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Text(
                            text = "খিলপাড়া, চাটখিল • অফিসারবৃন্দের মোবাইল নম্বর",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateTo(com.example.ui.viewmodel.ScreenRoute.HOME) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ফিরে যান",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:01769690011"))
                        context.startActivity(intent)
                    }) {
                        Icon(
                            imageVector = Icons.Default.PhoneInTalk,
                            contentDescription = "হটলাইন কল",
                            tint = PoliceGold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PoliceNavyPrimary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Police Center Hero Banner & Hotline Call
            item {
                PoliceCenterHeroCard(context = context)
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = { Text("অফিসারের নাম, পদবি বা বিট দিয়ে খুঁজুন...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            // Filter Chips
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(rankFilters) { filter ->
                        FilterChip(
                            selected = selectedRankFilter == filter,
                            onClick = { selectedRankFilter = filter },
                            label = { Text(filter, fontSize = 13.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PoliceNavyPrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // Count Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "মোট কর্মকর্তা: ${filteredOfficers.size} জন",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = PoliceNavyDark
                    )
                    Text(
                        text = "২৪ ঘণ্টা সেবা",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = EmergencyRed,
                        modifier = Modifier
                            .background(
                                color = EmergencyRed.copy(alpha = 0.12f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Empty State
            if (adminLoggedIn) {
                item {
                    Surface(
                        color = PoliceGold,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clickable { viewModel.navigateTo(com.example.ui.viewmodel.ScreenRoute.ADMIN_PANEL) }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = PoliceNavyPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "এডমিন মোড সক্রিয়: এখানে ক্লিক করে পুলিশ কর্মকর্তাদের তথ্য যুক্ত বা এডিট করুন",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = PoliceNavyPrimary,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(Icons.Default.Edit, contentDescription = null, tint = PoliceNavyPrimary)
                        }
                    }
                }
            }

            if (filteredOfficers.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.SearchOff,
                                contentDescription = "No results",
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "কোনো পুলিশ কর্মকর্তা খুঁজে পাওয়া যায়নি",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(filteredOfficers, key = { it.id }) { officer ->
                    val isFav = favorites.any { it.id == "police_${officer.id}" }
                    PoliceOfficerCard(
                        officer = officer,
                        isFavorite = isFav,
                        onToggleFavorite = {
                            viewModel.toggleFavorite(
                                id = "police_${officer.id}",
                                name = officer.name,
                                designation = "${officer.designation} (${officer.dutyArea})",
                                mobile = officer.mobile,
                                category = "পুলিশ কর্মকর্তা",
                                photoUrl = officer.photoUrl
                            )
                        },
                        context = context
                    )
                }
            }
        }
    }
}

@Composable
private fun PoliceCenterHeroCard(context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(PoliceNavyDark, PoliceNavyPrimary, PoliceNavyLight)
                    )
                )
                .padding(16.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(PoliceGold.copy(alpha = 0.2f), CircleShape)
                            .border(1.5.dp, PoliceGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalPolice,
                            contentDescription = "Police Badge",
                            tint = PoliceGold,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Text(
                            text = "চাটখিল থানা • নোয়াখালী জেলা পুলিশ",
                            fontSize = 13.sp,
                            color = PoliceGold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "আইন-শৃঙ্খলা রক্ষা, বিট পুলিশিং সেবা ও যেকোনো জরুরি প্রয়োজনে সরাসরি দায়িত্বরত অফিসারদের সাথে যোগাযোগ করুন।",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:01769690011"))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Incharge Call",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("ইনচার্জ কল", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:999"))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "National Emergency 999",
                            tint = EmergencyRed,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("৯৯৯ জাতীয় কল", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun PoliceOfficerCard(
    officer: PoliceOfficerEntity,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    context: Context
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                // Officer Photo
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(2.dp, PoliceNavyPrimary, CircleShape)
                        .background(PoliceNavyLight.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    if (officer.photoUrl.isNotBlank()) {
                        AsyncImage(
                            model = officer.photoUrl,
                            contentDescription = officer.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Officer Avatar",
                            tint = PoliceNavyPrimary,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = officer.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = onToggleFavorite,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) EmergencyRed else MaterialTheme.colorScheme.outline
                            )
                        }
                    }

                    // Rank & Designation
                    Text(
                        text = officer.designation,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PoliceNavyPrimary
                    )

                    if (officer.bpNumber.isNotBlank()) {
                        Text(
                            text = "বিপি নম্বর: ${officer.bpNumber}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Duty Area
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = PoliceGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = officer.dutyArea,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Bar
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhoneAndroid,
                            contentDescription = "Mobile",
                            tint = PoliceNavyPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = officer.mobile,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // SMS Button
                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:${officer.mobile}"))
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Sms,
                                contentDescription = "SMS Send",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        // Call Button
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${officer.mobile}"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2E7D32) // Emerald Green for call
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Call",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("কল করুন", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
