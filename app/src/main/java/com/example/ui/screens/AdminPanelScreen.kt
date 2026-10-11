package com.example.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.*
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.PoliceGold
import com.example.ui.theme.PoliceNavyPrimary
import com.example.ui.viewmodel.MainViewModel

@Composable
fun AdminPanelScreen(
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val adminLoggedIn by viewModel.adminLoggedIn.collectAsState()
    val adminPassword by viewModel.adminPassword.collectAsState()

    var inputPass by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showForgotDialog by remember { mutableStateOf(false) }
    var selectedCategoryTab by remember { mutableStateOf(0) }

    if (!adminLoggedIn) {
        // Password Protected Admin Login Card
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(64.dp),
                        shape = CircleShape,
                        color = PoliceNavyPrimary.copy(alpha = 0.1f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Admin Lock",
                                modifier = Modifier.size(36.dp),
                                tint = PoliceNavyPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "এডমিন প্যানেল লগইন",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))

                    // Helpful Hint Card displaying password
                    Surface(
                        color = PoliceGold.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Key,
                                contentDescription = null,
                                tint = PoliceNavyPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "এডমিন পাসওয়ার্ড: $adminPassword",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = PoliceNavyPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = inputPass,
                        onValueChange = { inputPass = it },
                        label = { Text("এডমিন পাসওয়ার্ড দিন") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Key, contentDescription = null, tint = PoliceNavyPrimary) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (passwordVisible) "পাসওয়ার্ড লুকান" else "পাসওয়ার্ড দেখুন",
                                    tint = PoliceNavyPrimary
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            if (viewModel.loginAdmin(inputPass)) {
                                Toast.makeText(context, "এডমিন প্যানেলে স্বাগতম!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "ভুল পাসওয়ার্ড! আবার চেষ্টা করুন", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PoliceNavyPrimary)
                    ) {
                        Icon(Icons.Default.Login, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("লগইন করুন", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Auto-fill button
                    OutlinedButton(
                        onClick = {
                            inputPass = adminPassword
                            if (viewModel.loginAdmin(adminPassword)) {
                                Toast.makeText(context, "এডমিন হিসেবে সফলভাবে লগইন হয়েছেন!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.FlashOn, contentDescription = null, tint = PoliceNavyPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("পাসওয়ার্ড ($adminPassword) দিয়ে সরাসরি লগইন", color = PoliceNavyPrimary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    TextButton(
                        onClick = { showForgotDialog = true }
                    ) {
                        Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(16.dp), tint = EmergencyRed)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("পাসওয়ার্ড ভুলে গেছেন? রিসেট করুন", color = EmergencyRed, fontSize = 13.sp)
                    }
                }
            }

            if (showForgotDialog) {
                AlertDialog(
                    onDismissRequest = { showForgotDialog = false },
                    icon = { Icon(Icons.Default.LockReset, contentDescription = null, tint = PoliceGold, modifier = Modifier.size(36.dp)) },
                    title = { Text("এডমিন পাসওয়ার্ড রিসেট", fontWeight = FontWeight.Bold) },
                    text = {
                        Column {
                            Text("এডমিন পাসওয়ার্ড পুনরায় মূল ডিফল্ট পাসওয়ার্ড 'admin123'-এ রিসেট করা হবে।")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("রিসেট করার পর পাসওয়ার্ড হবে: admin123", fontWeight = FontWeight.Bold, color = PoliceNavyPrimary)
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.resetAdminPassword()
                                inputPass = "admin123"
                                showForgotDialog = false
                                viewModel.loginAdmin("admin123")
                                Toast.makeText(context, "পাসওয়ার্ড 'admin123' এ রিসেট ও লগইন সম্পন্ন হয়েছে!", Toast.LENGTH_LONG).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PoliceNavyPrimary)
                        ) {
                            Text("রিসেট ও লগইন করুন")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showForgotDialog = false }) {
                            Text("বাতিল")
                        }
                    }
                )
            }
        }
    } else {
        // Logged-in Admin Management Center
        val scrollState = rememberScrollState()

        val members by viewModel.allMembers.collectAsState()
        val villagePolice by viewModel.allVillagePolice.collectAsState()
        val schools by viewModel.schoolsColleges.collectAsState()
        val cngs by viewModel.cngStations.collectAsState()
        val persons by viewModel.importantPersons.collectAsState()
        val policeOfficers by viewModel.policeOfficers.collectAsState()
        val notices by viewModel.notices.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
        ) {
            // Admin Top Header
            Surface(
                color = PoliceNavyPrimary,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin", tint = PoliceGold, modifier = Modifier.size(28.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("এডমিন কন্ট্রোল সেন্টার", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("স্বাগতম, সিস্টেম এডমিন", color = PoliceGold, fontSize = 12.sp)
                            }
                        }

                        Button(
                            onClick = { viewModel.logoutAdmin() },
                            colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.Logout, contentDescription = "Logout", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("লগআউট", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Easy Category Selector Chips (Horizontal Scrollable)
            Text(
                text = "সম্পাদনার জন্য ক্যাটাগরি নির্বাচন করুন:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            ScrollableTabRow(
                selectedTabIndex = selectedCategoryTab,
                edgePadding = 12.dp,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = PoliceNavyPrimary
            ) {
                AdminCategoryTab("চেয়ারম্যান তথ্য", Icons.Default.VerifiedUser, null, selectedCategoryTab == 0) { selectedCategoryTab = 0 }
                AdminCategoryTab("ইউপি সদস্য", Icons.Default.Groups, members.size, selectedCategoryTab == 1) { selectedCategoryTab = 1 }
                AdminCategoryTab("গ্রাম পুলিশ", Icons.Default.LocalPolice, villagePolice.size, selectedCategoryTab == 2) { selectedCategoryTab = 2 }
                AdminCategoryTab("স্কুল/কলেজ", Icons.Default.School, schools.size, selectedCategoryTab == 3) { selectedCategoryTab = 3 }
                AdminCategoryTab("সিএনজি স্ট্যান্ড", Icons.Default.DirectionsCar, cngs.size, selectedCategoryTab == 4) { selectedCategoryTab = 4 }
                AdminCategoryTab("গুরুত্বপূর্ণ ব্যক্তি", Icons.Default.PersonSearch, persons.size, selectedCategoryTab == 5) { selectedCategoryTab = 5 }
                AdminCategoryTab("পুলিশ কর্মকর্তা", Icons.Default.LocalPolice, policeOfficers.size, selectedCategoryTab == 6) { selectedCategoryTab = 6 }
                AdminCategoryTab("বিজ্ঞপ্তি ও নোটিশ", Icons.Default.Campaign, notices.size, selectedCategoryTab == 7) { selectedCategoryTab = 7 }
                AdminCategoryTab("ডেভেলপার ও অ্যাপ আইকন", Icons.Default.Code, null, selectedCategoryTab == 8) { selectedCategoryTab = 8 }
                AdminCategoryTab("জরুরি হটলাইন", Icons.Default.PhoneInTalk, null, selectedCategoryTab == 9) { selectedCategoryTab = 9 }
                AdminCategoryTab("পাসওয়ার্ড সেটিংস", Icons.Default.Security, null, selectedCategoryTab == 10) { selectedCategoryTab = 10 }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Category Content Area
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                when (selectedCategoryTab) {
                    0 -> ChairmanManagementSection(viewModel, context)
                    1 -> MemberManagementSection(viewModel, members, context)
                    2 -> VillagePoliceManagementSection(viewModel, villagePolice, context)
                    3 -> SchoolManagementSection(viewModel, schools, context)
                    4 -> CNGManagementSection(viewModel, cngs, context)
                    5 -> ImportantPersonsManagementSection(viewModel, persons, context)
                    6 -> PoliceOfficersManagementSection(viewModel, policeOfficers, context)
                    7 -> NoticeManagementSection(viewModel, notices, context)
                    8 -> DeveloperManagementSection(viewModel, context)
                    9 -> HotlineManagementSection(viewModel, context)
                    10 -> SecuritySettingsSection(viewModel, context)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun AdminCategoryTab(
    title: String,
    icon: ImageVector,
    badgeCount: Int?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Tab(
        selected = isSelected,
        onClick = onClick,
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
            ) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = if (isSelected) PoliceNavyPrimary else Color.Gray)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = title,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 13.sp,
                    color = if (isSelected) PoliceNavyPrimary else MaterialTheme.colorScheme.onSurface
                )
                if (badgeCount != null) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        color = if (isSelected) PoliceNavyPrimary else Color.LightGray,
                        shape = CircleShape
                    ) {
                        Text(
                            text = badgeCount.toString(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    )
}

fun saveUriToInternalStorage(context: android.content.Context, uri: Uri): String {
    return try {
        if (uri.scheme == "file") {
            return uri.toString()
        }
        val inputStream = context.contentResolver.openInputStream(uri) ?: return uri.toString()
        val imagesDir = java.io.File(context.filesDir, "app_images").apply { if (!exists()) mkdirs() }
        val file = java.io.File(imagesDir, "img_${System.currentTimeMillis()}_${(1000..9999).random()}.jpg")
        file.outputStream().use { output ->
            inputStream.copyTo(output)
        }
        Uri.fromFile(file).toString()
    } catch (e: Exception) {
        e.printStackTrace()
        uri.toString()
    }
}

// Reusable Image/Photo Picker Field with Mobile Gallery Integration
@Composable
fun PhotoPickerField(
    label: String,
    photoUrl: String,
    onPhotoSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val savedPath = saveUriToInternalStorage(context, it)
            onPhotoSelected(savedPath)
        }
    }

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PoliceNavyPrimary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                if (photoUrl.isNotBlank()) {
                    AsyncImage(
                        model = photoUrl,
                        contentDescription = "Preview",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = "Photo",
                        tint = PoliceNavyPrimary
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Button(
                    onClick = { photoPickerLauncher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = PoliceNavyPrimary),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("গ্যালারি থেকে ছবি যোগ করুন", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = photoUrl,
                    onValueChange = onPhotoSelected,
                    placeholder = { Text("অথবা ছবির Web URL দিন", fontSize = 11.sp) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
            }
        }
    }
}

// ----------------------------------------------------
// 0. CHAIRMAN MANAGEMENT SECTION
// ----------------------------------------------------
@Composable
fun ChairmanManagementSection(viewModel: MainViewModel, context: android.content.Context) {
    var selectedUnionId by remember { mutableStateOf("khilpara") }
    val currentChairman by viewModel.getChairmanFlow(selectedUnionId).collectAsState(initial = null)

    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var officeAddress by remember { mutableStateOf("") }
    var photoUrl by remember { mutableStateOf("") }

    LaunchedEffect(currentChairman) {
        currentChairman?.let {
            name = it.name
            mobile = it.mobile
            email = it.email
            officeAddress = it.officeAddress
            photoUrl = it.photoUrl
        }
    }

    Column {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = PoliceGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("ইউনিয়ন চেয়ারম্যান তথ্য ও ছবি পরিবর্তন", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = selectedUnionId == "khilpara", onClick = { selectedUnionId = "khilpara" }, label = { Text("১ নং খিলপাড়া") })
                    FilterChip(selected = selectedUnionId == "ramnarayanpur", onClick = { selectedUnionId = "ramnarayanpur" }, label = { Text("২ নং রামনারায়ণপুর") })
                    FilterChip(selected = selectedUnionId == "noakhola", onClick = { selectedUnionId = "noakhola" }, label = { Text("৩ নং নোয়াখোলা") })
                }

                Spacer(modifier = Modifier.height(12.dp))

                PhotoPickerField(
                    label = "চেয়ারম্যান সাহেবের ছবি (গ্যালারি বা URL):",
                    photoUrl = photoUrl,
                    onPhotoSelected = { photoUrl = it }
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("চেয়ারম্যানের নাম") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("মোবাইল নম্বর") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
                    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("ইমেইল ঠিকানা") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
                }
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = officeAddress, onValueChange = { officeAddress = it }, label = { Text("ইউনিয়ন পরিষদ কার্যালয়ের ঠিকানা") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        if (name.isNotBlank() && mobile.isNotBlank()) {
                            viewModel.updateChairman(selectedUnionId, name, photoUrl, mobile, email, officeAddress)
                            Toast.makeText(context, "চেয়ারম্যান সাহেবের তথ্য সফলভাবে হালনাগাদ হয়েছে!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "নাম ও মোবাইল নম্বর দিন", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PoliceNavyPrimary)
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("চেয়ারম্যান তথ্য আপডেট করুন", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// ----------------------------------------------------
// 1. MEMBER MANAGEMENT SECTION
// ----------------------------------------------------
@Composable
fun MemberManagementSection(viewModel: MainViewModel, members: List<UnionMemberEntity>, context: android.content.Context) {
    var editingMemberId by remember { mutableStateOf<Long?>(null) }
    var unionId by remember { mutableStateOf("khilpara") }
    var name by remember { mutableStateOf("") }
    var ward by remember { mutableStateOf("1") }
    var villages by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var photoUrl by remember { mutableStateOf("") }

    Column {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(if (editingMemberId != null) Icons.Default.Edit else Icons.Default.PersonAdd, contentDescription = null, tint = EmeraldGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (editingMemberId != null) "ইউপি সদস্যের তথ্য সম্পাদনা করুন" else "নতুন ইউপি সদস্য যুক্ত করুন", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Union Selector
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = unionId == "khilpara",
                        onClick = { unionId = "khilpara" },
                        label = { Text("১ নং খিলপাড়া") }
                    )
                    FilterChip(
                        selected = unionId == "ramnarayanpur",
                        onClick = { unionId = "ramnarayanpur" },
                        label = { Text("২ নং রামনারায়ণপুর") }
                    )
                    FilterChip(
                        selected = unionId == "noakhola",
                        onClick = { unionId = "noakhola" },
                        label = { Text("৩ নং নোয়াখোলা") }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                PhotoPickerField(
                    label = "মেম্বারের ছবি (গ্যালারি থেকে নির্বাচন):",
                    photoUrl = photoUrl,
                    onPhotoSelected = { photoUrl = it }
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("মেম্বারের নাম") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = ward, onValueChange = { ward = it }, label = { Text("ওয়ার্ড (১-৯)") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
                    OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("মোবাইল নম্বর") }, modifier = Modifier.weight(2f), shape = RoundedCornerShape(10.dp))
                }
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = villages, onValueChange = { villages = it }, label = { Text("গ্রামসমূহ (কমা দিয়ে আলাদা করুন)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                Spacer(modifier = Modifier.height(14.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (editingMemberId != null) {
                        OutlinedButton(
                            onClick = {
                                editingMemberId = null
                                name = ""
                                mobile = ""
                                villages = ""
                                photoUrl = ""
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("বাতিল")
                        }
                    }

                    Button(
                        onClick = {
                            if (name.isNotBlank() && mobile.isNotBlank()) {
                                viewModel.addMember(unionId, name, ward.toIntOrNull() ?: 1, villages, mobile, photoUrl, id = editingMemberId ?: 0L)
                                Toast.makeText(context, if (editingMemberId != null) "মেম্বার তথ্য সফলভাবে আপডেট করা হয়েছে!" else "মেম্বার তথ্য সফলভাবে সংরক্ষিত হয়েছে!", Toast.LENGTH_SHORT).show()
                                editingMemberId = null
                                name = ""
                                mobile = ""
                                villages = ""
                                photoUrl = ""
                            } else {
                                Toast.makeText(context, "নাম ও মোবাইল নম্বর দেওয়া বাধ্যতামূলক", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
                    ) {
                        Icon(if (editingMemberId != null) Icons.Default.Save else Icons.Default.AddCircle, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (editingMemberId != null) "আপডেট করুন" else "সংরক্ষণ করুন", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "বর্তমান ইউপি সদস্য তালিকা (${members.size} জন):",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        members.forEach { member ->
            AdminItemCard(
                title = member.name,
                subtitle = "ওয়ার্ড নং ${member.wardNumber} | গ্রাম: ${member.villages}",
                phone = member.mobile,
                photoUrl = member.photoUrl,
                tag = when(member.unionId) {
                    "khilpara" -> "খিলপাড়া"
                    "ramnarayanpur" -> "রামনারায়ণপুর"
                    else -> "নোয়াখোলা"
                },
                onEdit = {
                    editingMemberId = member.id
                    unionId = member.unionId
                    name = member.name
                    ward = member.wardNumber.toString()
                    villages = member.villages
                    mobile = member.mobile
                    photoUrl = member.photoUrl
                },
                onDelete = {
                    viewModel.deleteMember(member.id)
                    Toast.makeText(context, "মেম্বার তথ্য মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show()
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// ----------------------------------------------------
// 2. VILLAGE POLICE MANAGEMENT SECTION
// ----------------------------------------------------
@Composable
fun VillagePoliceManagementSection(viewModel: MainViewModel, policeList: List<VillagePoliceEntity>, context: android.content.Context) {
    var editingPoliceId by remember { mutableStateOf<Long?>(null) }
    var unionId by remember { mutableStateOf("khilpara") }
    var name by remember { mutableStateOf("") }
    var villages by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var photoUrl by remember { mutableStateOf("") }

    Column {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(if (editingPoliceId != null) Icons.Default.Edit else Icons.Default.LocalPolice, contentDescription = null, tint = PoliceNavyPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (editingPoliceId != null) "গ্রাম পুলিশ সদস্য তথ্য রি-এডিট করুন" else "নতুন গ্রাম পুলিশ / চৌকিদার সদস্য যুক্ত করুন", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = unionId == "khilpara", onClick = { unionId = "khilpara" }, label = { Text("১ নং খিলপাড়া") })
                    FilterChip(selected = unionId == "ramnarayanpur", onClick = { unionId = "ramnarayanpur" }, label = { Text("২ নং রামনারায়ণপুর") })
                    FilterChip(selected = unionId == "noakhola", onClick = { unionId = "noakhola" }, label = { Text("৩ নং নোয়াখোলা") })
                }

                Spacer(modifier = Modifier.height(8.dp))

                PhotoPickerField(
                    label = "চৌকিদার / গ্রাম পুলিশের ছবি (গ্যালারি থেকে):",
                    photoUrl = photoUrl,
                    onPhotoSelected = { photoUrl = it }
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("নাম (চৌকিদার / দফাদার)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("মোবাইল নম্বর") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = villages, onValueChange = { villages = it }, label = { Text("দায়িত্বপ্রাপ্ত গ্রামসমূহ") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                Spacer(modifier = Modifier.height(14.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (editingPoliceId != null) {
                        OutlinedButton(
                            onClick = {
                                editingPoliceId = null
                                name = ""
                                mobile = ""
                                villages = ""
                                photoUrl = ""
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("বাতিল")
                        }
                    }

                    Button(
                        onClick = {
                            if (name.isNotBlank() && mobile.isNotBlank()) {
                                viewModel.addVillagePolice(unionId, name, villages, mobile, photoUrl, id = editingPoliceId ?: 0L)
                                Toast.makeText(context, if (editingPoliceId != null) "গ্রাম পুলিশ তথ্য আপডেট হয়েছে!" else "গ্রাম পুলিশ সদস্য তথ্য সংরক্ষিত হয়েছে!", Toast.LENGTH_SHORT).show()
                                editingPoliceId = null
                                name = ""
                                mobile = ""
                                villages = ""
                                photoUrl = ""
                            } else {
                                Toast.makeText(context, "নাম ও মোবাইল নম্বর দিন", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PoliceNavyPrimary)
                    ) {
                        Icon(if (editingPoliceId != null) Icons.Default.Save else Icons.Default.AddCircle, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (editingPoliceId != null) "আপডেট করুন" else "সংরক্ষণ করুন", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "বর্তমান গ্রাম পুলিশ বাহিনী (${policeList.size} জন):",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        policeList.forEach { p ->
            AdminItemCard(
                title = p.name,
                subtitle = "দায়িত্বপ্রাপ্ত গ্রাম: ${p.responsibleVillages}",
                phone = p.mobile,
                photoUrl = p.photoUrl,
                tag = "গ্রাম পুলিশ",
                onEdit = {
                    editingPoliceId = p.id
                    unionId = p.unionId
                    name = p.name
                    villages = p.responsibleVillages
                    mobile = p.mobile
                    photoUrl = p.photoUrl
                },
                onDelete = {
                    viewModel.deleteVillagePolice(p.id)
                    Toast.makeText(context, "তথ্য মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show()
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// ----------------------------------------------------
// 3. SCHOOL MANAGEMENT SECTION
// ----------------------------------------------------
@Composable
fun SchoolManagementSection(viewModel: MainViewModel, schools: List<SchoolCollegeEntity>, context: android.content.Context) {
    var editingSchoolId by remember { mutableStateOf<Long?>(null) }
    var name by remember { mutableStateOf("") }
    var headTeacher by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("উচ্চ বিদ্যালয়") }
    var logoUrl by remember { mutableStateOf("") }

    Column {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(if (editingSchoolId != null) Icons.Default.Edit else Icons.Default.School, contentDescription = null, tint = PoliceNavyPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (editingSchoolId != null) "শিক্ষা প্রতিষ্ঠানের তথ্য রি-এডিট করুন" else "নতুন শিক্ষা প্রতিষ্ঠান যুক্ত করুন", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = type == "উচ্চ বিদ্যালয়", onClick = { type = "উচ্চ বিদ্যালয়" }, label = { Text("উচ্চ বিদ্যালয়") })
                    FilterChip(selected = type == "প্রাথমিক বিদ্যালয়", onClick = { type = "প্রাথমিক বিদ্যালয়" }, label = { Text("প্রাথমিক") })
                    FilterChip(selected = type == "কলেজ", onClick = { type = "কলেজ" }, label = { Text("কলেজ") })
                    FilterChip(selected = type == "মাদ্রাসা", onClick = { type = "মাদ্রাসা" }, label = { Text("মাদ্রাসা") })
                }

                Spacer(modifier = Modifier.height(8.dp))

                PhotoPickerField(
                    label = "প্রতিষ্ঠানের লোগো (গ্যালারি থেকে বেছে নিন):",
                    photoUrl = logoUrl,
                    onPhotoSelected = { logoUrl = it }
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("প্রতিষ্ঠানের পূর্ণ নাম") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = headTeacher, onValueChange = { headTeacher = it }, label = { Text("প্রধান শিক্ষক / অধ্যক্ষের নাম") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("মোবাইল নম্বর") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
                    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("ইমেইল (ঐচ্ছিক)") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (editingSchoolId != null) {
                        OutlinedButton(
                            onClick = {
                                editingSchoolId = null
                                name = ""
                                headTeacher = ""
                                mobile = ""
                                email = ""
                                logoUrl = ""
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("বাতিল")
                        }
                    }

                    Button(
                        onClick = {
                            if (name.isNotBlank() && mobile.isNotBlank()) {
                                viewModel.addSchool(name, headTeacher, "প্রধান শিক্ষক/অধ্যক্ষ", mobile, email, type, logoUrl, id = editingSchoolId ?: 0L)
                                Toast.makeText(context, if (editingSchoolId != null) "শিক্ষা প্রতিষ্ঠান তথ্য আপডেট হয়েছে!" else "শিক্ষা প্রতিষ্ঠান সফলভাবে সংরক্ষিত হয়েছে!", Toast.LENGTH_SHORT).show()
                                editingSchoolId = null
                                name = ""
                                headTeacher = ""
                                mobile = ""
                                email = ""
                                logoUrl = ""
                            } else {
                                Toast.makeText(context, "প্রতিষ্ঠানের নাম ও মোবাইল নম্বর দিন", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PoliceNavyPrimary)
                    ) {
                        Icon(if (editingSchoolId != null) Icons.Default.Save else Icons.Default.AddCircle, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (editingSchoolId != null) "আপডেট করুন" else "সংরক্ষণ করুন", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "বর্তমান শিক্ষা প্রতিষ্ঠান তালিকা (${schools.size} টি):",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        schools.forEach { school ->
            AdminItemCard(
                title = school.name,
                subtitle = "প্রধান শিক্ষক/অধ্যক্ষ: ${school.headTeacher}",
                phone = school.mobile,
                photoUrl = school.logoUrl,
                tag = school.type,
                onEdit = {
                    editingSchoolId = school.id
                    name = school.name
                    headTeacher = school.headTeacher
                    mobile = school.mobile
                    email = school.email
                    type = school.type
                    logoUrl = school.logoUrl
                },
                onDelete = {
                    viewModel.deleteSchool(school.id)
                    Toast.makeText(context, "শিক্ষা প্রতিষ্ঠান মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show()
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// ----------------------------------------------------
// 4. CNG MANAGEMENT SECTION
// ----------------------------------------------------
@Composable
fun CNGManagementSection(viewModel: MainViewModel, cngs: List<CNGStationEntity>, context: android.content.Context) {
    var editingCNGId by remember { mutableStateOf<Long?>(null) }
    var stationName by remember { mutableStateOf("") }
    var standName by remember { mutableStateOf("") }
    var linemanName by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }

    Column {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(if (editingCNGId != null) Icons.Default.Edit else Icons.Default.DirectionsCar, contentDescription = null, tint = EmeraldGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (editingCNGId != null) "সিএনজি তথ্য রি-এডিট করুন" else "নতুন সিএনজি স্টেশন / লাইনম্যান যুক্ত করুন", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(value = stationName, onValueChange = { stationName = it }, label = { Text("স্টেশনের নাম (যেমন: খিলপাড়া সিএনজি স্ট্যান্ড)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = standName, onValueChange = { standName = it }, label = { Text("রুটের নাম (যেমন: খিলপাড়া - চাটখিল)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = linemanName, onValueChange = { linemanName = it }, label = { Text("লাইনম্যান / মাস্টারের নাম") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
                    OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("মোবাইল নম্বর") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (editingCNGId != null) {
                        OutlinedButton(
                            onClick = {
                                editingCNGId = null
                                stationName = ""
                                standName = ""
                                linemanName = ""
                                mobile = ""
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("বাতিল")
                        }
                    }

                    Button(
                        onClick = {
                            if (stationName.isNotBlank() && mobile.isNotBlank()) {
                                viewModel.addCNGStation(stationName, standName, linemanName, mobile, "খিলপাড়া বাজার", id = editingCNGId ?: 0L)
                                Toast.makeText(context, if (editingCNGId != null) "সিএনজি তথ্য আপডেট হয়েছে!" else "সিএনজি তথ্য সফলভাবে সংরক্ষিত হয়েছে!", Toast.LENGTH_SHORT).show()
                                editingCNGId = null
                                stationName = ""
                                standName = ""
                                linemanName = ""
                                mobile = ""
                            } else {
                                Toast.makeText(context, "স্টেশনের নাম ও মোবাইল নম্বর দেওয়া আবশ্যক", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen)
                    ) {
                        Icon(if (editingCNGId != null) Icons.Default.Save else Icons.Default.AddCircle, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (editingCNGId != null) "আপডেট করুন" else "সংরক্ষণ করুন", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "বর্তমান সিএনজি স্ট্যান্ড ডিরেক্টরি (${cngs.size} টি):",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        cngs.forEach { cng ->
            AdminItemCard(
                title = cng.stationName,
                subtitle = "রুট: ${cng.standName} | লাইনম্যান: ${cng.linemanName}",
                phone = cng.mobile,
                tag = "সিএনজি স্ট্যান্ড",
                onEdit = {
                    editingCNGId = cng.id
                    stationName = cng.stationName
                    standName = cng.standName
                    linemanName = cng.linemanName
                    mobile = cng.mobile
                },
                onDelete = {
                    viewModel.deleteCNGStation(cng.id)
                    Toast.makeText(context, "সিএনজি তথ্য মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show()
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// ----------------------------------------------------
// 5. IMPORTANT PERSONS MANAGEMENT SECTION
// ----------------------------------------------------
@Composable
fun ImportantPersonsManagementSection(viewModel: MainViewModel, persons: List<ImportantPersonEntity>, context: android.content.Context) {
    var editingPersonId by remember { mutableStateOf<Long?>(null) }
    var category by remember { mutableStateOf("ডাক্তার") }
    var name by remember { mutableStateOf("") }
    var designation by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var photoUrl by remember { mutableStateOf("") }

    Column {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(if (editingPersonId != null) Icons.Default.Edit else Icons.Default.PersonSearch, contentDescription = null, tint = PoliceNavyPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (editingPersonId != null) "গুরুত্বপূর্ণ ব্যক্তির তথ্য রি-এডিট করুন" else "নতুন গুরুত্বপূর্ণ ব্যক্তি যুক্ত করুন", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    FilterChip(selected = category == "ডাক্তার", onClick = { category = "ডাক্তার" }, label = { Text("ডাক্তার") })
                    FilterChip(selected = category == "শিক্ষক", onClick = { category = "শিক্ষক" }, label = { Text("শিক্ষক") })
                    FilterChip(selected = category == "আইনজীবী", onClick = { category = "আইনজীবী" }, label = { Text("আইনজীবী") })
                    FilterChip(selected = category == "মুক্তিযোদ্ধা", onClick = { category = "মুক্তিযোদ্ধা" }, label = { Text("মুক্তিযোদ্ধা") })
                }

                Spacer(modifier = Modifier.height(8.dp))

                PhotoPickerField(
                    label = "ব্যক্তির ছবি (গ্যালারি থেকে বেছে নিন):",
                    photoUrl = photoUrl,
                    onPhotoSelected = { photoUrl = it }
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("ব্যক্তির পূর্ণ নাম") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = designation, onValueChange = { designation = it }, label = { Text("পদবী / স্পেশালিটি (যেমন: এমবিবিএস, চর্ম ও যৌন)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("মোবাইল নম্বর") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
                    OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("চেম্বার / ঠিকানা") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (editingPersonId != null) {
                        OutlinedButton(
                            onClick = {
                                editingPersonId = null
                                name = ""
                                designation = ""
                                mobile = ""
                                address = ""
                                photoUrl = ""
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("বাতিল")
                        }
                    }

                    Button(
                        onClick = {
                            if (name.isNotBlank() && mobile.isNotBlank()) {
                                viewModel.addImportantPerson(category, name, designation, mobile, address, photoUrl, id = editingPersonId ?: 0L)
                                Toast.makeText(context, if (editingPersonId != null) "ব্যক্তির তথ্য আপডেট হয়েছে!" else "তথ্য সফলভাবে সংরক্ষিত হয়েছে!", Toast.LENGTH_SHORT).show()
                                editingPersonId = null
                                name = ""
                                designation = ""
                                mobile = ""
                                address = ""
                                photoUrl = ""
                            } else {
                                Toast.makeText(context, "নাম ও ফোন নম্বর দিন", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PoliceNavyPrimary)
                    ) {
                        Icon(if (editingPersonId != null) Icons.Default.Save else Icons.Default.AddCircle, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (editingPersonId != null) "আপডেট করুন" else "সংরক্ষণ করুন", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "গুরুত্বপূর্ণ ব্যক্তি তালিকা (${persons.size} জন):",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        persons.forEach { p ->
            AdminItemCard(
                title = p.name,
                subtitle = "${p.designation} | ঠিকানা: ${p.address}",
                phone = p.mobile,
                photoUrl = p.photoUrl,
                tag = p.category,
                onEdit = {
                    editingPersonId = p.id
                    category = p.category
                    name = p.name
                    designation = p.designation
                    mobile = p.mobile
                    address = p.address
                    photoUrl = p.photoUrl
                },
                onDelete = {
                    viewModel.deleteImportantPerson(p.id)
                    Toast.makeText(context, "ব্যক্তির তথ্য মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show()
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// ----------------------------------------------------
// POLICE OFFICERS MANAGEMENT SECTION (POLICE CENTER)
// ----------------------------------------------------
@Composable
fun PoliceOfficersManagementSection(
    viewModel: MainViewModel,
    policeOfficers: List<PoliceOfficerEntity>,
    context: android.content.Context
) {
    var editingOfficerId by remember { mutableStateOf<Long?>(null) }
    var name by remember { mutableStateOf("") }
    var designation by remember { mutableStateOf("") }
    var rank by remember { mutableStateOf("উপ-পরিদর্শক (এসআই)") }
    var mobile by remember { mutableStateOf("") }
    var bpNumber by remember { mutableStateOf("") }
    var photoUrl by remember { mutableStateOf("") }
    var dutyArea by remember { mutableStateOf("খিলপাড়া পুলিশ তদন্ত কেন্দ্র") }

    Column {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        if (editingOfficerId != null) Icons.Default.Edit else Icons.Default.LocalPolice,
                        contentDescription = null,
                        tint = PoliceNavyPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        if (editingOfficerId != null) "পুলিশ কর্মকর্তার তথ্য এডিট করুন" else "নতুন পুলিশ কর্মকর্তা যুক্ত করুন",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("অফিসারের নাম") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = designation,
                    onValueChange = { designation = it },
                    label = { Text("পদবি (যেমন: ইনচার্জ / পরিদর্শক, বিট অফিসার)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = rank,
                    onValueChange = { rank = it },
                    label = { Text("র‍্যাংক/ক্যাটাগরি (পরিদর্শক/এসআই/এএসআই/ডিউটি অফিসার)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = mobile,
                    onValueChange = { mobile = it },
                    label = { Text("মোবাইল নম্বর") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = bpNumber,
                    onValueChange = { bpNumber = it },
                    label = { Text("বিপি নম্বর (ঐচ্ছিক)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = dutyArea,
                    onValueChange = { dutyArea = it },
                    label = { Text("দায়িত্বপ্রাপ্ত এলাকা/বিট") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))

                PhotoPickerField(
                    label = "অফিসারের ছবি (গ্যালারি বা URL):",
                    photoUrl = photoUrl,
                    onPhotoSelected = { photoUrl = it }
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (editingOfficerId != null) {
                        OutlinedButton(
                            onClick = {
                                editingOfficerId = null
                                name = ""
                                designation = ""
                                rank = "উপ-পরিদর্শক (এসআই)"
                                mobile = ""
                                bpNumber = ""
                                photoUrl = ""
                                dutyArea = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র"
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("বাতিল")
                        }
                    }

                    Button(
                        onClick = {
                            if (name.isNotBlank() && mobile.isNotBlank()) {
                                viewModel.addPoliceOfficer(
                                    name = name,
                                    designation = designation,
                                    rank = rank,
                                    mobile = mobile,
                                    bpNumber = bpNumber,
                                    photoUrl = photoUrl,
                                    dutyArea = dutyArea,
                                    id = editingOfficerId ?: 0L
                                )
                                Toast.makeText(
                                    context,
                                    if (editingOfficerId != null) "অফিসারের তথ্য আপডেট করা হয়েছে!" else "নতুন কর্মকর্তা সংরক্ষিত হয়েছে!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                editingOfficerId = null
                                name = ""
                                designation = ""
                                rank = "উপ-পরিদর্শক (এসআই)"
                                mobile = ""
                                bpNumber = ""
                                photoUrl = ""
                                dutyArea = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র"
                            } else {
                                Toast.makeText(context, "নাম ও মোবাইল নম্বর প্রদান করুন", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PoliceNavyPrimary)
                    ) {
                        Icon(if (editingOfficerId != null) Icons.Default.Save else Icons.Default.AddCircle, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (editingOfficerId != null) "আপডেট করুন" else "সংরক্ষণ করুন", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "তদন্ত কেন্দ্র কর্মকর্তা তালিকা (${policeOfficers.size} জন):",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        policeOfficers.forEach { officer ->
            AdminItemCard(
                title = officer.name,
                subtitle = "${officer.designation} (${officer.dutyArea})",
                phone = officer.mobile,
                photoUrl = officer.photoUrl,
                tag = officer.rank,
                onEdit = {
                    editingOfficerId = officer.id
                    name = officer.name
                    designation = officer.designation
                    rank = officer.rank
                    mobile = officer.mobile
                    bpNumber = officer.bpNumber
                    photoUrl = officer.photoUrl
                    dutyArea = officer.dutyArea
                },
                onDelete = {
                    viewModel.deletePoliceOfficer(officer.id)
                    Toast.makeText(context, "অফিসারের তথ্য মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show()
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// ----------------------------------------------------
// 6. NOTICE MANAGEMENT SECTION
// ----------------------------------------------------
@Composable
fun NoticeManagementSection(viewModel: MainViewModel, notices: List<NoticeEntity>, context: android.content.Context) {
    var editingNoticeId by remember { mutableStateOf<Long?>(null) }
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("জরুরি আইন-শৃঙ্খলা") }
    var content by remember { mutableStateOf("") }
    var isEmergency by remember { mutableStateOf(false) }

    Column {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(if (editingNoticeId != null) Icons.Default.Edit else Icons.Default.Campaign, contentDescription = null, tint = EmergencyRed)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (editingNoticeId != null) "বিজ্ঞপ্তি রি-এডিট / পরিবর্তন করুন" else "নতুন পুলিশ বিজ্ঞপ্তি প্রকাশ করুন", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("বিজ্ঞপ্তির শিরোনাম") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("বিজ্ঞপ্তির বিস্তারিত তথ্য") }, modifier = Modifier.fillMaxWidth(), minLines = 3, shape = RoundedCornerShape(10.dp))

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isEmergency, onCheckedChange = { isEmergency = it }, colors = CheckboxDefaults.colors(checkedColor = EmergencyRed))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("জরুরি হট সংবাদ হিসেবে লাল ফ্ল্যাশ দেখান", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (isEmergency) EmergencyRed else MaterialTheme.colorScheme.onSurface)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (editingNoticeId != null) {
                        OutlinedButton(
                            onClick = {
                                editingNoticeId = null
                                title = ""
                                content = ""
                                isEmergency = false
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("বাতিল")
                        }
                    }

                    Button(
                        onClick = {
                            if (title.isNotBlank() && content.isNotBlank()) {
                                viewModel.addNotice(title, category, content, "২০২৬-০৭-৩০", isEmergency, id = editingNoticeId ?: 0L)
                                Toast.makeText(context, if (editingNoticeId != null) "বিজ্ঞপ্তি আপডেট করা হয়েছে!" else "বিজ্ঞপ্তি সফলভাবে প্রকাশিত হয়েছে!", Toast.LENGTH_SHORT).show()
                                editingNoticeId = null
                                title = ""
                                content = ""
                                isEmergency = false
                            } else {
                                Toast.makeText(context, "শিরোনাম ও বিবরণ দিন", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed)
                    ) {
                        Icon(if (editingNoticeId != null) Icons.Default.Save else Icons.Default.Send, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (editingNoticeId != null) "আপডেট করুন" else "বিজ্ঞপ্তি প্রকাশ করুন", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "প্রকাশিত বিজ্ঞপ্তি তালিকা (${notices.size} টি):",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        notices.forEach { notice ->
            AdminItemCard(
                title = notice.title,
                subtitle = notice.content,
                phone = notice.date,
                tag = if (notice.isEmergency) "জরুরি হট নোটিশ" else notice.category,
                onEdit = {
                    editingNoticeId = notice.id
                    title = notice.title
                    category = notice.category
                    content = notice.content
                    isEmergency = notice.isEmergency
                },
                onDelete = {
                    viewModel.deleteNotice(notice.id)
                    Toast.makeText(context, "বিজ্ঞপ্তি মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show()
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// ----------------------------------------------------
// HOTLINE MANAGEMENT SECTION
// ----------------------------------------------------
@Composable
fun HotlineManagementSection(viewModel: MainViewModel, context: android.content.Context) {
    val hotlineNumbers by viewModel.hotlineNumbers.collectAsState()
    val currentHotlines = hotlineNumbers ?: com.example.data.local.InitialData.defaultHotlineNumbers

    var title1 by remember(currentHotlines) { mutableStateOf(currentHotlines.title1) }
    var number1 by remember(currentHotlines) { mutableStateOf(currentHotlines.number1) }
    var title2 by remember(currentHotlines) { mutableStateOf(currentHotlines.title2) }
    var number2 by remember(currentHotlines) { mutableStateOf(currentHotlines.number2) }
    var title3 by remember(currentHotlines) { mutableStateOf(currentHotlines.title3) }
    var number3 by remember(currentHotlines) { mutableStateOf(currentHotlines.number3) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.PhoneInTalk, contentDescription = null, tint = PoliceNavyPrimary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("জরুরি হটলাইন নম্বরসমূহ সম্পাদনা", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Hotline 1
            Text("১ম হটলাইন বাটন:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = PoliceNavyPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title1, onValueChange = { title1 = it }, label = { Text("শিরোনাম") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
                OutlinedTextField(value = number1, onValueChange = { number1 = it }, label = { Text("মোবাইল/নম্বর") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Hotline 2
            Text("২য় হটলাইন বাটন:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = PoliceNavyPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title2, onValueChange = { title2 = it }, label = { Text("শিরোনাম") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
                OutlinedTextField(value = number2, onValueChange = { number2 = it }, label = { Text("মোবাইল/নম্বর") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Hotline 3
            Text("৩য় হটলাইন বাটন:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = PoliceNavyPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title3, onValueChange = { title3 = it }, label = { Text("শিরোনাম") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
                OutlinedTextField(value = number3, onValueChange = { number3 = it }, label = { Text("মোবাইল/নম্বর") }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(10.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (title1.isNotBlank() && number1.isNotBlank() &&
                        title2.isNotBlank() && number2.isNotBlank() &&
                        title3.isNotBlank() && number3.isNotBlank()) {
                        viewModel.updateHotlineNumbers(title1, number1, title2, number2, title3, number3)
                        Toast.makeText(context, "হটলাইন নম্বরসমূহ সফলভাবে আপডেট হয়েছে!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "সবগুলো শিরোনাম ও নম্বর সঠিকভাবে পূরণ করুন", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PoliceNavyPrimary)
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("হটলাইন নম্বর আপডেট করুন", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ----------------------------------------------------
// 7. SECURITY & PASSWORD MANAGEMENT SECTION
// ----------------------------------------------------
@Composable
fun SecuritySettingsSection(viewModel: MainViewModel, context: android.content.Context) {
    val currentPassword by viewModel.adminPassword.collectAsState()
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Key, contentDescription = null, tint = PoliceNavyPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("এডমিন পাসওয়ার্ড পরিবর্তন (নিজের মতো সেট করুন)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "বর্তমান সক্রিয় পাসওয়ার্ড: $currentPassword",
                    style = MaterialTheme.typography.bodySmall,
                    color = PoliceGold,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("নতুন কাস্টম পাসওয়ার্ড দিন") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("নতুন পাসওয়ার্ড পুনঃরায় প্রবেশ করুন") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        if (newPassword.isNotBlank() && newPassword == confirmPassword) {
                            viewModel.changeAdminPassword(newPassword)
                            Toast.makeText(context, "এডমিন পাসওয়ার্ড সফলভাবে পরিবর্তিত হয়েছে! নতুন পাসওয়ার্ড: $newPassword", Toast.LENGTH_LONG).show()
                            newPassword = ""
                            confirmPassword = ""
                        } else {
                            Toast.makeText(context, "পাসওয়ার্ড মিলছে না বা খালি রাখা যাবে না", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PoliceNavyPrimary)
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("পাসওয়ার্ড আপডেট ও সেভ করুন", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Restore, contentDescription = null, tint = EmergencyRed)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("অফিসিয়াল ডাটাবেস রিসেট", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "অ্যাপ্লিকেশনের সকল কন্টাক্ট ডাটাবেস মূল অফিশিয়াল তথ্যে রিসেট করতে নীচের বাটনে চাপুন।",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedButton(
                    onClick = {
                        viewModel.resetInitialData()
                        Toast.makeText(context, "ডাটাবেস সফলভাবে রিসেট করা হয়েছে!", Toast.LENGTH_LONG).show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = EmergencyRed),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("অফিসিয়াল ডাটাবেসে রিসেট করুন", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// ----------------------------------------------------
// REUSABLE ADMIN ITEM CARD WITH PHOTO PREVIEW & DELETE ACTION
// ----------------------------------------------------
@Composable
fun AdminItemCard(
    title: String,
    subtitle: String,
    phone: String,
    tag: String,
    photoUrl: String = "",
    onEdit: (() -> Unit)? = null,
    onDelete: () -> Unit
) {
    var showConfirmDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (photoUrl.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(PoliceNavyPrimary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = photoUrl,
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = PoliceNavyPrimary.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = tag,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PoliceNavyPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface)
                Text(text = subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                if (phone.isNotBlank()) {
                    Text(text = "📞 $phone", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = EmeraldGreen)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (onEdit != null) {
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(PoliceNavyPrimary.copy(alpha = 0.1f))
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = PoliceNavyPrimary)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                }

                IconButton(
                    onClick = { showConfirmDialog = true },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(EmergencyRed.copy(alpha = 0.1f))
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = EmergencyRed)
                }
            }
        }
    }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("আইটেমটি মুছে ফেলতে চান?", fontWeight = FontWeight.Bold) },
            text = { Text("'$title' তথ্যটি সম্পূর্ণভাবে মুছে যাবে। আপনি কি নিশ্চিত?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete()
                        showConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EmergencyRed)
                ) {
                    Text("হ্যাঁ, মুছে ফেলুন")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("বাতিল")
                }
            }
        )
    }
}

@Composable
fun DeveloperManagementSection(viewModel: MainViewModel, context: android.content.Context) {
    val devInfoState by viewModel.developerInfo.collectAsState()
    val devInfo = devInfoState ?: com.example.data.local.InitialData.defaultDeveloperInfo

    var name by remember(devInfo) { mutableStateOf(devInfo.name) }
    var title by remember(devInfo) { mutableStateOf(devInfo.title) }
    var photoUrl by remember(devInfo) { mutableStateOf(devInfo.photoUrl) }
    var email by remember(devInfo) { mutableStateOf(devInfo.email) }
    var phone by remember(devInfo) { mutableStateOf(devInfo.phone) }
    var bio by remember(devInfo) { mutableStateOf(devInfo.bio) }
    var appIconUrl by remember(devInfo) { mutableStateOf(devInfo.appIconUrl) }
    var appName by remember(devInfo) { mutableStateOf(devInfo.appName) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "ডেভেলপার তথ্য ও অ্যাপস আইকন এডিটর",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = PoliceNavyPrimary
            )
            Text(
                text = "এখানে অ্যাপসের ডেভেলপার প্রোফাইল, ছবি এবং অ্যাপ আইকন/ব্র্যান্ডিং আপডেট করা যাবে।",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // App Branding Fields
            Text("১. অ্যাপ ব্র্যান্ডিং ও আইকন:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = appName,
                onValueChange = { appName = it },
                label = { Text("অ্যাপের নাম / শিরোনাম") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            PhotoPickerField(
                label = "অ্যাপ আইকন / লোগো ছবি (URL / গ্যালারি)",
                photoUrl = appIconUrl,
                onPhotoSelected = { appIconUrl = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Developer Profile Fields
            Text("২. ডেভেলপার প্রোফাইল ও তথ্য:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("ডেভেলপার / টিমের নাম") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("পদবী / ঠিকানা") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            PhotoPickerField(
                label = "ডেভেলপার এর ছবি (URL / গ্যালারি)",
                photoUrl = photoUrl,
                onPhotoSelected = { photoUrl = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("ইমেইল নম্বর") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("মোবাইল নম্বর") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = { Text("প্রজেক্ট বিবরণ / বায়ো (Bio)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.updateDeveloperInfo(
                        name = name,
                        title = title,
                        photoUrl = photoUrl,
                        email = email,
                        phone = phone,
                        bio = bio,
                        appIconUrl = appIconUrl,
                        appName = appName
                    )
                    Toast.makeText(context, "ডেভেলপার তথ্য ও অ্যাপ আইকন সফলভাবে আপডেট করা হয়েছে!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PoliceNavyPrimary)
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("তথ্য সংরক্ষণ করুন", fontWeight = FontWeight.Bold)
            }
        }
    }
}
