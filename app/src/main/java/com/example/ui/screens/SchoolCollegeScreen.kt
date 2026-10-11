package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ContactCard
import com.example.ui.viewmodel.MainViewModel

@Composable
fun SchoolCollegeScreen(
    viewModel: MainViewModel
) {
    val schoolsColleges by viewModel.schoolsColleges.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    var selectedFilter by remember { mutableStateOf("সব") } // "সব", "স্কুল", "কলেজ"

    val filteredList = when (selectedFilter) {
        "স্কুল" -> schoolsColleges.filter { it.type == "স্কুল" }
        "কলেজ" -> schoolsColleges.filter { it.type == "কলেজ" }
        else -> schoolsColleges
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedFilter == "সব",
                onClick = { selectedFilter = "সব" },
                label = { Text("সকল প্রতিষ্ঠান (${schoolsColleges.size})") }
            )
            FilterChip(
                selected = selectedFilter == "স্কুল",
                onClick = { selectedFilter = "স্কুল" },
                label = { Text("স্কুল") }
            )
            FilterChip(
                selected = selectedFilter == "কলেজ",
                onClick = { selectedFilter = "কলেজ" },
                label = { Text("কলেজ/মাদ্রাসা") }
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredList) { school ->
                val isFav = favorites.any { it.id == "school_${school.id}" }
                ContactCard(
                    name = school.name,
                    designationOrWard = "${school.designation}: ${school.headTeacher}",
                    mobile = school.mobile,
                    addressOrVillages = "ইমেইল: ${school.email.ifBlank { "N/A" }}",
                    photoUrl = school.logoUrl,
                    email = school.email,
                    mapsUrl = school.mapsUrl,
                    isFavorite = isFav,
                    onFavoriteToggle = {
                        viewModel.toggleFavorite(
                            id = "school_${school.id}",
                            name = school.name,
                            designation = "${school.designation}: ${school.headTeacher}",
                            mobile = school.mobile,
                            category = school.type,
                            photoUrl = school.logoUrl
                        )
                    }
                )
            }
        }
    }
}
