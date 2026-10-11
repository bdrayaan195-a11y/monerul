package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PoliceNavyPrimary

@Composable
fun PrivacyPolicyScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.PrivacyTip, contentDescription = "Privacy", tint = PoliceNavyPrimary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "গোপনীয়তা নীতি (Privacy Policy)",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "তথ্য সংগ্রহ ও ব্যবহার",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র ও ইউনিয়ন তথ্যাবলি অ্যাপটি ব্যবহারকারীদের ব্যক্তিগত ডাটা বা গোপনীয় তথ্য সংগ্রহ কিংবা বাণিজ্যিক স্বার্থে বিক্রি করে না। অ্যাপে প্রদর্শিত সকল টেলিফোন নম্বর ও ঠিকানা প্রকাশ্য জনপ্রতিনিধি ও প্রাতিষ্ঠানিক নির্দেশিকা থেকে সংরক্ষিত।\n\n" +
                            "১. স্থানীয় ফোন কল ও মেসেজিং: অ্যাপের ফোন কল ও হোয়াটসঅ্যাপ কন্টাক্ট অ্যাকশন ব্যবহারকারীর সম্মতি সাপেক্ষে সরাসরি ডিভাইস অ্যাপের মাধ্যমে শুরু হয়।\n\n" +
                            "২. রুম ডাটাবেস সিকিউরিটি: আপনার ফেভারিট পরিচিতি ও স্থানীয় চ্যাট হিস্ট্রি সম্পূর্ণ আপনার ডিভাইসের অফলাইন রুম ডাটাবেসে সংরক্ষিত থাকে।\n\n" +
                            "৩. জরুরি সহায়তা: জরুরি ৯৯৯ কল সরাসরি জাতীয় জরুরি সেবা ডেস্কে প্রেরিত হয়।",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
