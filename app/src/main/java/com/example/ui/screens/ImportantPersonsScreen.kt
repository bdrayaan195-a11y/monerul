package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ContactCard
import com.example.ui.viewmodel.MainViewModel

@Composable
fun ImportantPersonsScreen(
    viewModel: MainViewModel
) {
    val importantPersons by viewModel.importantPersons.collectAsState()
    val favorites by viewModel.favorites.collectAsState()

    var selectedCategory by remember { mutableStateOf("সকল") }

    val categories = listOf("সকল", "চিকিৎসক", "আইনজীবী", "শিক্ষক", "প্রকৌশলী", "বীর মুক্তিযোদ্ধা", "ধর্মীয় নেতা", "সমাজসেবক")

    val filteredPersons = if (selectedCategory == "সকল") {
        importantPersons
    } else {
        importantPersons.filter { it.category == selectedCategory }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Horizontal Scroll Category Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(category) }
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredPersons) { person ->
                val isFav = favorites.any { it.id == "person_${person.id}" }
                ContactCard(
                    name = person.name,
                    designationOrWard = "${person.category} • ${person.designation}",
                    mobile = person.mobile,
                    addressOrVillages = "ঠিকানা/কর্মস্থল: ${person.address}",
                    photoUrl = person.photoUrl,
                    isFavorite = isFav,
                    onFavoriteToggle = {
                        viewModel.toggleFavorite(
                            id = "person_${person.id}",
                            name = person.name,
                            designation = "${person.category} • ${person.designation}",
                            mobile = person.mobile,
                            category = person.category,
                            photoUrl = person.photoUrl
                        )
                    }
                )
            }
        }
    }
}
