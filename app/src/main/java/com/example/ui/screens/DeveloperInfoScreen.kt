package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.local.InitialData
import com.example.ui.theme.PoliceGold
import com.example.ui.theme.PoliceNavyPrimary
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.ScreenRoute

@Composable
fun DeveloperInfoScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val devInfoState by viewModel.developerInfo.collectAsState()
    val devInfo = devInfoState ?: InitialData.defaultDeveloperInfo

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Icon & Branding Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PoliceNavyPrimary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(84.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.White)
                        .border(2.dp, PoliceGold, RoundedCornerShape(18.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (devInfo.appIconUrl.isNotBlank()) {
                        AsyncImage(
                            model = devInfo.appIconUrl,
                            contentDescription = "App Icon",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Apps,
                            contentDescription = "App Icon",
                            tint = PoliceNavyPrimary,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = devInfo.appName.ifBlank { "খিলপাড়া পুলিশ তদন্ত কেন্দ্র" },
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "সংস্করণ v1.2.0 (অফিসিয়াল)",
                    style = MaterialTheme.typography.bodySmall,
                    color = PoliceGold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Developer Profile Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(PoliceNavyPrimary)
                        .border(3.dp, PoliceGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (devInfo.photoUrl.isNotBlank()) {
                        AsyncImage(
                            model = devInfo.photoUrl,
                            contentDescription = "Developer Photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = "Dev Icon",
                            tint = PoliceGold,
                            modifier = Modifier.size(54.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = devInfo.name.ifBlank { "ডিজিটাল অ্যাপস ডেভেলপার টিম" },
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = devInfo.title.ifBlank { "চাটখিল, নোয়াখালী • বাংলাদেশ" },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Project Description / Bio
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = null,
                        tint = PoliceNavyPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "প্রজেক্ট ভিশন ও কারিগরি বিবরণ",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = devInfo.bio.ifBlank {
                        "খিলপাড়া পুলিশ তদন্ত কেন্দ্র ও ইউনিয়ন তথ্যাবলি অ্যাপটি বাংলাদেশ পুলিশের নাগরিক সেবা দ্রুতকরণ, আইনশৃঙ্খলা বজায় রাখা এবং ৩টি ইউনিয়নের (খিলপাড়া, রামনারায়ণপুর, নোয়াখোলা) সর্বস্তরের জনগণের তথ্য সুবিধা প্রদানের জন্য নির্মিত সম্পূর্ণ ডিজিটাল প্লাটফর্ম।"
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Contact Buttons
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "যোগাযোগ ও সাপোর্ট",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(12.dp))

                if (devInfo.email.isNotBlank()) {
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${devInfo.email}"))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PoliceNavyPrimary)
                    ) {
                        Icon(Icons.Default.Email, contentDescription = "Email")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ইমেইল: ${devInfo.email}")
                    }
                }

                if (devInfo.phone.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${devInfo.phone}"))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = "Phone")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("কল করুন: ${devInfo.phone}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Direct link to Admin Panel for Editing Developer Info
        OutlinedButton(
            onClick = { viewModel.navigateTo(ScreenRoute.ADMIN_PANEL) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin")
            Spacer(modifier = Modifier.width(8.dp))
            Text("এডমিন প্যানেল থেকে তথ্য ও আইকন পরিবর্তন করুন")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

