package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChatMessageEntity
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.PoliceGold
import com.example.ui.theme.PoliceNavyLight
import com.example.ui.theme.PoliceNavyPrimary
import com.example.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChatScreen(
    viewModel: MainViewModel
) {
    val chatMessages by viewModel.chatMessages.collectAsState()
    val adminLoggedIn by viewModel.adminLoggedIn.collectAsState()

    var messageText by remember { mutableStateOf("") }
    var isEmergencyMsg by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Chat Header Bar
        Surface(
            color = PoliceNavyPrimary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(PoliceGold.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Shield",
                        tint = PoliceGold,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "সিটিজেন হটলাইন ও চ্যাট",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        color = Color.White
                    )
                    Text(
                        text = if (adminLoggedIn) "এডমিন হিসেবে সংযুক্ত" else "খিলপাড়া পুলিশ ইনচার্জ ও থানা প্রশাসন",
                        style = MaterialTheme.typography.labelSmall,
                        color = PoliceGold
                    )
                }
            }
        }

        // Messages Feed List
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(chatMessages) { msg ->
                ChatMessageBubble(msg = msg)
            }
        }

        // Emergency Toggle Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 14.dp, vertical = 2.dp)
        ) {
            Checkbox(
                checked = isEmergencyMsg,
                onCheckedChange = { isEmergencyMsg = it },
                colors = CheckboxDefaults.colors(checkedColor = EmergencyRed)
            )
            Text(
                text = "এটি একটি জরুরি বার্তা (High Priority)",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = if (isEmergencyMsg) EmergencyRed else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Input Field and Send Button
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("বার্তা টাইপ করুন...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(8.dp))

                FloatingActionButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            val senderName = if (adminLoggedIn) "ইনচার্জ (এডমিন)" else "নাগরিক"
                            viewModel.sendChatMessage(messageText, senderName, isEmergencyMsg)
                            messageText = ""
                            isEmergencyMsg = false
                        }
                    },
                    containerColor = if (isEmergencyMsg) EmergencyRed else PoliceNavyPrimary,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatMessageBubble(msg: ChatMessageEntity) {
    val isOfficial = msg.senderRole == "এডমিন" || msg.senderRole == "পুলিশ কর্মকর্তা"
    val sdf = SimpleDateFormat("hh:mm a, dd MMM", Locale.getDefault())
    val formattedTime = sdf.format(Date(msg.timestamp))

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isOfficial) Alignment.Start else Alignment.End
    ) {
        Surface(
            color = when {
                msg.isEmergency -> EmergencyRed.copy(alpha = 0.15f)
                isOfficial -> PoliceNavyLight
                else -> MaterialTheme.colorScheme.surfaceVariant
            },
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isOfficial) 4.dp else 16.dp,
                bottomEnd = if (isOfficial) 16.dp else 4.dp
            ),
            border = if (msg.isEmergency) CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.Brush.horizontalGradient(listOf(EmergencyRed, EmergencyRed))) else null,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = msg.senderName,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = if (isOfficial) PoliceGold else MaterialTheme.colorScheme.primary
                    )
                    if (msg.isEmergency) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Warning, contentDescription = "Emergency", tint = EmergencyRed, modifier = Modifier.size(14.dp))
                            Text("জরুরি", color = EmergencyRed, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = msg.messageText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isOfficial) Color.White else MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formattedTime,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = if (isOfficial) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
