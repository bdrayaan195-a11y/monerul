package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ContactCard
import com.example.ui.theme.EmergencyRed
import com.example.ui.viewmodel.MainViewModel

@Composable
fun FavoritesScreen(
    viewModel: MainViewModel
) {
    val favorites by viewModel.favorites.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = EmergencyRed
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "সংরক্ষিত প্রিয় পরিচিতিসমূহ (${favorites.size})",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }

        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "No Favorites",
                        modifier = Modifier.size(64.dp),
                        tint = EmergencyRed.copy(alpha = 0.4f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "আপনার কোনো পরিচিতি বুকমার্ক করা নেই। পরিচিতি কার্ডের লাভ (❤️) আইকনে ট্যাপ করে এখানে যুক্ত করুন।",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(favorites) { fav ->
                    ContactCard(
                        name = fav.name,
                        designationOrWard = "${fav.category} • ${fav.designation}",
                        mobile = fav.mobile,
                        photoUrl = fav.photoUrl,
                        isFavorite = true,
                        onFavoriteToggle = {
                            viewModel.toggleFavorite(
                                id = fav.id,
                                name = fav.name,
                                designation = fav.designation,
                                mobile = fav.mobile,
                                category = fav.category,
                                photoUrl = fav.photoUrl
                            )
                        }
                    )
                }
            }
        }
    }
}
