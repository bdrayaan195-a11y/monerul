package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ContactCard
import com.example.ui.theme.PoliceNavyPrimary
import com.example.ui.viewmodel.MainViewModel

@Composable
fun CNGStationScreen(
    viewModel: MainViewModel
) {
    val cngStations by viewModel.cngStations.collectAsState()
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
                    imageVector = Icons.Default.DirectionsCar,
                    contentDescription = "CNG",
                    tint = PoliceNavyPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "সিএনজি স্টেশন ও চালক/লাইনম্যান তালিকা",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(cngStations) { station ->
                val isFav = favorites.any { it.id == "cng_${station.id}" }
                ContactCard(
                    name = station.stationName,
                    designationOrWard = "স্ট্যান্ড: ${station.standName} | লাইনম্যান: ${station.linemanName}",
                    mobile = station.mobile,
                    addressOrVillages = "অবস্থান: ${station.location}",
                    isFavorite = isFav,
                    onFavoriteToggle = {
                        viewModel.toggleFavorite(
                            id = "cng_${station.id}",
                            name = station.stationName,
                            designation = "লাইনম্যান: ${station.linemanName}",
                            mobile = station.mobile,
                            category = "CNG স্টেশন"
                        )
                    }
                )
            }
        }
    }
}
