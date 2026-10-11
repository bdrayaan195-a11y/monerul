package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.PoliceGold
import com.example.ui.theme.PoliceNavyPrimary

@Composable
fun AboutAppScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(PoliceNavyPrimary)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_khilpara_police_logo_1785418299126),
                contentDescription = "Logo",
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র ও ইউনিয়ন তথ্যাবলি",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "সংস্করণ v1.0.0 (অফিসিয়াল প্রোডাকশন)",
            style = MaterialTheme.typography.labelMedium,
            color = PoliceGold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "অ্যাপ্লিকেশনের উদ্দেশ্য ও বৈশিষ্ট্যাবলি",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "১. খিলপাড়া পুলিশ তদন্ত কেন্দ্রের ইনচার্জ ও ডিউটি অফিসারের সাথে সরাসরি ৯৯৯ ও জরুরি চ্যাট ডায়াল ব্যবস্থা।\n\n" +
                            "২. ১ নং খিলপাড়া, ২ নং রামনারায়ণপুর ও ৩ নং নোয়াখোলা ইউনিয়নের চেয়ারম্যান, ৯টি ওয়ার্ডের মেম্বার ও গ্রাম পুলিশ (চৌকিদার) বাহিনীর সচিত্র ডায়রেক্টরি।\n\n" +
                            "৩. এলাকার সকল সরকারি প্রাথমিক ও উচ্চ বিদ্যালয়, কলেজ ও মাদ্রাসার ইমেইল, প্রধান শিক্ষক ও ভৌগোলিক তথ্য।\n\n" +
                            "৪. স্থানীয় সিএনজি স্ট্যান্ডের মাস্টার ও লাইনম্যানদের যোগাযোগের নম্বর যাতে যাতায়াতে তাৎক্ষণিক সহায়তা মেলে।\n\n" +
                            "৫. এলাকার ডাক্তার, শিক্ষক, আইনজীবী, মুক্তিযোদ্ধা ও সমাজসেবকদের নির্ভরযোগ্য ফোন নম্বর ডিরেক্টরি।",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
