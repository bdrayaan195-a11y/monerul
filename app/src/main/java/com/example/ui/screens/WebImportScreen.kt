package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.PoliceNavyPrimary
import com.example.ui.viewmodel.MainViewModel

@Composable
fun WebImportScreen(
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    var remoteUrl by remember { mutableStateOf("https://raw.githubusercontent.com/example/khilpara-police-data/main/data.json") }
    var jsonText by remember { mutableStateOf("") }
    var isSyncing by remember { mutableStateOf(false) }
    var exportedJson by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CloudDownload, contentDescription = "Import", tint = PoliceNavyPrimary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "ওয়েব ও অনলাইন ডাটা সিঙ্ক",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 1. Direct Web URL Sync
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "১. অনলাইন সার্ভার থেকে সরাসরি সিঙ্ক",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    color = PoliceNavyPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "অনলাইন JSON সার্ভার বা GitHub Link থেকে সর্বশেষ চেয়ারম্যান, মেম্বার, গ্রামপুলিশ ও সকল তথ্যাবলি এক ক্লিকে ডাউনলোড করুন।",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = remoteUrl,
                    onValueChange = { remoteUrl = it },
                    label = { Text("অনলাইন JSON সার্ভার URL") },
                    leadingIcon = { Icon(Icons.Default.Link, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        isSyncing = true
                        viewModel.syncFromRemoteUrl(remoteUrl) { success, message ->
                            isSyncing = false
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        }
                    },
                    enabled = !isSyncing,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = PoliceNavyPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    if (isSyncing) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("সিঙ্ক হচ্ছে...")
                    } else {
                        Icon(Icons.Default.CloudDownload, contentDescription = "Sync")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("অনলাইন থেকে সিঙ্ক করুন", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Manual JSON Paste Import
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "২. ম্যানুয়াল JSON ডাটা পেস্ট করে ইমপোর্ট",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "এডমিন কর্তৃক প্রকাশিত সম্পূর্ণ JSON কোড এখানে পেস্ট করে সরাসরি অ্যাপসের লোকাল ডাটাবেস আপডেট করুন।",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = jsonText,
                    onValueChange = { jsonText = it },
                    placeholder = { Text("এখানে JSON ডাটা পেস্ট করুন...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (jsonText.isNotBlank()) {
                            viewModel.importWebData(jsonText) { count ->
                                Toast.makeText(context, "$count টি আইটেম সফলভাবে লোকাল ডাটাবেসে ইমপোর্ট করা হয়েছে!", Toast.LENGTH_LONG).show()
                                jsonText = ""
                            }
                        } else {
                            Toast.makeText(context, "অনুগ্রহ করে JSON টেক্সট ইনপুট দিন", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Download, contentDescription = "Import Now")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("ডাটাবেসে ইমপোর্ট করুন", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Export Database as JSON for Admin
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "৩. এডমিন ডাটা এক্সপোর্ট ও পাবলিশ",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "বর্তমান অ্যাপসের সম্পূর্ণ ডাটাবেস (সকল চেয়ারম্যান, মেম্বার, নোটিশ ইত্যাদি) JSON ফরম্যাটে কপি করে সার্ভারে আপলোড করুন।",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = {
                        viewModel.exportDatabase { json ->
                            exportedJson = json
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("App Database JSON", json)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "সম্পূর্ণ ডাটাবেস JSON ক্লিপবোর্ডে কপি করা হয়েছে!", Toast.LENGTH_LONG).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = "Copy JSON")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("সম্পূর্ণ JSON কপি করুন")
                }

                if (exportedJson.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "JSON কপি সম্পন্ন হয়েছে (${exportedJson.length} অক্ষর)",
                        fontSize = 12.sp,
                        color = EmeraldGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

