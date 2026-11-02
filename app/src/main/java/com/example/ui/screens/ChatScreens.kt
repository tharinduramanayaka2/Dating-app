package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.model.AppLanguage
import com.example.model.ChatMessage
import com.example.model.MatchItem
import com.example.ui.theme.CoralAccent
import com.example.ui.theme.GoldSuperLike
import com.example.ui.theme.OnlineGreen
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RoseLight
import com.example.ui.theme.RosePrimary

@Composable
fun ChatListScreen(
    matches: List<MatchItem>,
    language: AppLanguage,
    onOpenChat: (MatchItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSinhala = language == AppLanguage.SINHALA
    var searchQuery by remember { mutableStateOf("") }

    val filteredMatches = remember(matches, searchQuery) {
        if (searchQuery.isBlank()) matches
        else matches.filter { it.profile.name.contains(searchQuery, ignoreCase = true) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isSinhala) "ගැළපීම් සහ කතාබහ" else "Matches & Chats",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(if (isSinhala) "නමින් සොයන්න..." else "Search matches...")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                focusedBorderColor = RosePrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("chat_search_field")
        )

        Spacer(modifier = Modifier.height(16.dp))

        // New Matches Story Row
        Text(
            text = if (isSinhala) "නව ගැළපීම්" else "New Matches",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            items(matches) { match ->
                MatchStoryItem(
                    match = match,
                    onClick = { onOpenChat(match) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Conversations Section
        Text(
            text = if (isSinhala) "පණිවිඩ" else "Messages",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (filteredMatches.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isSinhala) "පණිවිඩ හමු නොවීය" else "No messages found",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredMatches) { match ->
                    ConversationItem(
                        match = match,
                        onClick = { onOpenChat(match) }
                    )
                }
            }
        }
    }
}

@Composable
fun MatchStoryItem(
    match: MatchItem,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .testTag("match_story_${match.id}")
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .background(
                    Brush.sweepGradient(listOf(RosePrimary, CoralAccent, GoldSuperLike, RosePrimary)),
                    CircleShape
                )
                .padding(2.5.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
                .padding(2.5.dp)
                .clip(CircleShape)
        ) {
            AsyncImage(
                model = match.profile.photos.firstOrNull(),
                contentDescription = match.profile.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = match.profile.name,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
    }
}

@Composable
fun ConversationItem(
    match: MatchItem,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("chat_conversation_${match.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar with online dot
            Box(modifier = Modifier.size(54.dp)) {
                AsyncImage(
                    model = match.profile.photos.firstOrNull(),
                    contentDescription = match.profile.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
                if (match.isOnline) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .background(OnlineGreen, CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
                            .align(Alignment.BottomEnd)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Message Content
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${match.profile.name}, ${match.profile.age}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = match.lastMessageTime ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = match.lastMessage ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (match.unreadCount > 0) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (match.unreadCount > 0) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    if (match.unreadCount > 0) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(RosePrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = match.unreadCount.toString(),
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatConversationScreen(
    match: MatchItem,
    messages: List<ChatMessage>,
    language: AppLanguage,
    onBack: () -> Unit,
    onSendMessage: (String, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSinhala = language == AppLanguage.SINHALA
    var inputText by remember { mutableStateOf("") }
    var showGiftDialog by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    val icebreakers = if (isSinhala) SampleData.icebreakersSi else SampleData.icebreakersEn

    // Auto scroll to bottom when new message arrives
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Chat Header
        Surface(
            shadowElevation = 4.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("chat_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Box(modifier = Modifier.size(42.dp)) {
                    AsyncImage(
                        model = match.profile.photos.firstOrNull(),
                        contentDescription = match.profile.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                    if (match.isOnline) {
                        Box(
                            modifier = Modifier
                                .size(11.dp)
                                .background(OnlineGreen, CircleShape)
                                .border(1.5.dp, MaterialTheme.colorScheme.surface, CircleShape)
                                .align(Alignment.BottomEnd)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = match.profile.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = if (isSinhala) "දැන් ක්‍රියාකාරීයි" else "Active now",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnlineGreen,
                        fontSize = 11.sp
                    )
                }

                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = "Call", tint = RosePrimary)
                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Videocam, contentDescription = "Video", tint = RosePrimary)
                }
            }
        }

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Matched notification banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    ) {
                        Text(
                            text = if (isSinhala)
                                "ඔබ සහ ${match.profile.name} ගැළපී ඇත • ${match.matchedAt}"
                            else
                                "You matched with ${match.profile.name} • ${match.matchedAt}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            items(messages) { message ->
                MessageBubble(message = message)
            }
        }

        // Icebreaker Quick Suggestions
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = if (isSinhala) "💡 කතාබහ ඇරඹීමට අදහස්:" else "💡 Quick Icebreakers:",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(icebreakers) { icebreaker ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            RoseLight.copy(alpha = 0.4f)
                        ),
                        modifier = Modifier.clickable {
                            onSendMessage(icebreaker, null)
                        }
                    ) {
                        Text(
                            text = icebreaker,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Chat Input Bar
        Surface(
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Send Gift Button
                IconButton(
                    onClick = { showGiftDialog = true },
                    modifier = Modifier.testTag("open_gift_dialog_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.CardGiftcard,
                        contentDescription = "Send Gift",
                        tint = RosePrimary
                    )
                }

                // Text Input
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = {
                        Text(if (isSinhala) "පණිවිඩයක් ලියන්න..." else "Type a message...")
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RosePrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_input_text_field"),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Send Button
                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            onSendMessage(inputText.trim(), null)
                            inputText = ""
                        }
                    },
                    modifier = Modifier
                        .size(46.dp)
                        .background(RosePrimary, CircleShape)
                        .testTag("send_chat_message_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }

    if (showGiftDialog) {
        GiftPickerDialog(
            language = language,
            onDismiss = { showGiftDialog = false },
            onSendGift = { giftType, giftMsg ->
                onSendMessage(giftMsg, giftType)
                showGiftDialog = false
            }
        )
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    val isMe = message.isFromMe

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isMe) Alignment.End else Alignment.Start
    ) {
        if (message.giftType != null) {
            // Special Gift Card Message
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isMe) RosePrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, RoseLight.copy(alpha = 0.6f)),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val giftEmoji = when (message.giftType) {
                        "rose" -> "🌹"
                        "chocolate" -> "🍫"
                        "coffee" -> "☕"
                        else -> "💖"
                    }
                    Text(text = giftEmoji, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = message.text,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = message.timestamp,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            // Regular Message Bubble
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp,
                            bottomStart = if (isMe) 18.dp else 4.dp,
                            bottomEnd = if (isMe) 4.dp else 18.dp
                        )
                    )
                    .background(
                        if (isMe) {
                            Brush.linearGradient(listOf(RosePrimary, CoralAccent))
                        } else {
                            Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                        }
                    )
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Text(
                    text = message.text,
                    color = if (isMe) Color.White else MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = message.timestamp,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp, start = 4.dp, end = 4.dp)
            )
        }
    }
}

@Composable
fun GiftPickerDialog(
    language: AppLanguage,
    onDismiss: () -> Unit,
    onSendGift: (String, String) -> Unit
) {
    val isSinhala = language == AppLanguage.SINHALA

    val gifts = listOf(
        Triple("rose", "🌹", if (isSinhala) "රතු රෝස මලක් යැව්වා! 🌹" else "Sent you a red rose! 🌹"),
        Triple("chocolate", "🍫", if (isSinhala) "රසවත් චොකලට් තෑග්ගක්! 🍫" else "Sweet chocolate box for you! 🍫"),
        Triple("coffee", "☕", if (isSinhala) "උණුසුම් කෝපි සංග්‍රහයක්! ☕" else "Treating you to a coffee! ☕"),
        Triple("spark", "💖", if (isSinhala) "විශේෂ ආදර තරුවක්! 💖" else "Sent a special love spark! 💖")
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isSinhala) "තෑග්ගක් යවන්න 🎁" else "Send a Virtual Gift 🎁",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    gifts.forEach { (type, emoji, msg) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable { onSendGift(type, msg) }
                                .padding(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(RoseLight.copy(alpha = 0.2f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = emoji, fontSize = 28.sp)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = type.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onDismiss) {
                    Text(if (isSinhala) "අවලංගු කරන්න" else "Cancel")
                }
            }
        }
    }
}
