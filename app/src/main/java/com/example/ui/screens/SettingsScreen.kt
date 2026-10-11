package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.MainViewModel

@Composable
fun SettingsScreen(
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val isBengali by viewModel.isBengali.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "অ্যাপ্লিকেশন সেটিংস",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        // Dark Theme Switch
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Brightness6, contentDescription = "Dark Theme")
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("ডার্ক থিম মোড (Night Mode)", fontWeight = FontWeight.Bold)
                        Text("রাতের ব্যবহারে চোখের সুরক্ষার জন্য", style = MaterialTheme.typography.labelSmall)
                    }
                }
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { viewModel.toggleDarkMode() }
                )
            }
        }

        // Language Switch
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Language, contentDescription = "Language")
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("ভাষা নির্বাচন (Language)", fontWeight = FontWeight.Bold)
                        Text(if (isBengali) "বাংলা (Bengali)" else "English", style = MaterialTheme.typography.labelSmall)
                    }
                }
                Switch(
                    checked = isBengali,
                    onCheckedChange = { viewModel.toggleLanguage() }
                )
            }
        }

        // Sync & Reset Data Button
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CloudDownload, contentDescription = "Sync Data")
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("ডাটাবেস লোকাল ডাটা রি-সিঙ্ক", fontWeight = FontWeight.Bold)
                        Text("কোনো পরিচিতি অনুপস্থিত থাকলে পুনরায় সিঙ্ক করুন", style = MaterialTheme.typography.labelSmall)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = {
                        viewModel.resetInitialData()
                        Toast.makeText(context, "ডাটাবেস সফলভাবে রি-সিঙ্ক ও রিসেট করা হয়েছে", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("প্রাথমিক তথ্য রি-লোড করুন")
                }
            }
        }
    }
}
