package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.AppLanguage
import com.example.ui.DatingViewModel
import com.example.ui.components.FilterBottomSheet
import com.example.ui.components.MatchCelebrationDialog
import com.example.ui.components.ProfileDetailBottomSheet
import com.example.ui.screens.ChatConversationScreen
import com.example.ui.screens.ChatListScreen
import com.example.ui.screens.DiscoverScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.TopPicksScreen
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RoseLight
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.SparkDatingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SparkDatingTheme {
                DatingAppRoot()
            }
        }
    }
}

enum class NavigationTab(
    val titleEn: String,
    val titleSi: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    DISCOVER("Discover", "කැමැත්ත", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
    TOP_PICKS("Top Picks", "විශේෂ", Icons.Filled.Star, Icons.Outlined.StarBorder),
    CHATS("Chats", "කතාබහ", Icons.Filled.ChatBubble, Icons.Outlined.ChatBubbleOutline),
    PROFILE("Profile", "ගිණුම", Icons.Filled.Person, Icons.Outlined.PersonOutline)
}

@Composable
fun DatingAppRoot(viewModel: DatingViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(NavigationTab.DISCOVER) }
    var showFilterSheet by remember { mutableStateOf(false) }

    val isSinhala = uiState.language == AppLanguage.SINHALA
    val totalUnread = uiState.matches.sumOf { it.unreadCount }

    // Hardware back button behavior
    BackHandler(enabled = true) {
        when {
            uiState.selectedDetailProfile != null -> {
                viewModel.closeProfileDetail()
            }
            uiState.activeChatMatch != null -> {
                viewModel.closeChat()
            }
            selectedTab != NavigationTab.DISCOVER -> {
                selectedTab = NavigationTab.DISCOVER
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // Hide bottom navigation if inside active chat conversation
            if (uiState.activeChatMatch == null) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    NavigationTab.entries.forEach { tab ->
                        val isSelected = selectedTab == tab
                        val title = if (isSinhala) tab.titleSi else tab.titleEn
                        val icon = if (isSelected) tab.selectedIcon else tab.unselectedIcon

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { selectedTab = tab },
                            icon = {
                                if (tab == NavigationTab.CHATS && totalUnread > 0) {
                                    BadgedBox(
                                        badge = {
                                            Badge(containerColor = RosePrimary) {
                                                Text(
                                                    text = totalUnread.toString(),
                                                    color = androidx.compose.ui.graphics.Color.White,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    ) {
                                        androidx.compose.material3.Icon(
                                            imageVector = icon,
                                            contentDescription = title
                                        )
                                    }
                                } else {
                                    androidx.compose.material3.Icon(
                                        imageVector = icon,
                                        contentDescription = title
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = title,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = RosePrimary,
                                selectedTextColor = RosePrimary,
                                indicatorColor = RoseLight.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Main Screen Routing
            if (uiState.activeChatMatch != null) {
                // Active Chat Conversation Screen
                val currentMatch = uiState.activeChatMatch!!
                val currentMessages = uiState.chatMessages[currentMatch.id] ?: emptyList()
                ChatConversationScreen(
                    match = currentMatch,
                    messages = currentMessages,
                    language = uiState.language,
                    onBack = { viewModel.closeChat() },
                    onSendMessage = { text, giftType ->
                        viewModel.sendMessage(currentMatch.id, text, giftType)
                    }
                )
            } else {
                AnimatedContent(
                    targetState = selectedTab,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "TabContentTransition"
                ) { tab ->
                    when (tab) {
                        NavigationTab.DISCOVER -> {
                            DiscoverScreen(
                                deck = uiState.deck,
                                canRewind = uiState.swipedHistory.isNotEmpty(),
                                language = uiState.language,
                                likesCount = uiState.likesCount,
                                onSwipeRight = { profile -> viewModel.swipeRight(profile) },
                                onSwipeLeft = { profile -> viewModel.swipeLeft(profile) },
                                onSuperLike = { profile -> viewModel.superLike(profile) },
                                onRewind = { viewModel.rewind() },
                                onReloadDeck = { viewModel.reloadDeck() },
                                onOpenFilters = { showFilterSheet = true },
                                onToggleLanguage = { viewModel.toggleLanguage() },
                                onOpenProfileDetail = { profile -> viewModel.openProfileDetail(profile) }
                            )
                        }

                        NavigationTab.TOP_PICKS -> {
                            TopPicksScreen(
                                topPicks = uiState.topPicks,
                                likesReceived = uiState.likesReceived,
                                likesCount = uiState.likesCount,
                                language = uiState.language,
                                onLikeProfile = { profile -> viewModel.swipeRight(profile) },
                                onSuperLikeProfile = { profile -> viewModel.superLike(profile) },
                                onOpenProfileDetail = { profile -> viewModel.openProfileDetail(profile) }
                            )
                        }

                        NavigationTab.CHATS -> {
                            ChatListScreen(
                                matches = uiState.matches,
                                language = uiState.language,
                                onOpenChat = { match -> viewModel.openChat(match) }
                            )
                        }

                        NavigationTab.PROFILE -> {
                            ProfileScreen(
                                userProfile = uiState.userProfile,
                                language = uiState.language,
                                onToggleLanguage = { viewModel.toggleLanguage() },
                                onUpdateBio = { newBio -> viewModel.updateUserBio(newBio) }
                            )
                        }
                    }
                }
            }

            // Match Celebration Dialog
            if (uiState.newMatchProfile != null) {
                val matched = uiState.newMatchProfile!!
                MatchCelebrationDialog(
                    matchedProfile = matched,
                    userProfile = uiState.userProfile,
                    language = uiState.language,
                    onSendMessage = {
                        val matchItem = uiState.matches.find { it.profile.id == matched.id }
                            ?: uiState.matches.firstOrNull()
                        viewModel.dismissMatchDialog()
                        if (matchItem != null) {
                            selectedTab = NavigationTab.CHATS
                            viewModel.openChat(matchItem)
                        }
                    },
                    onKeepSwiping = {
                        viewModel.dismissMatchDialog()
                    }
                )
            }

            // Profile Detail Bottom Sheet
            if (uiState.selectedDetailProfile != null) {
                val detailProfile = uiState.selectedDetailProfile!!
                ProfileDetailBottomSheet(
                    profile = detailProfile,
                    language = uiState.language,
                    onDismiss = { viewModel.closeProfileDetail() },
                    onLike = {
                        viewModel.swipeRight(detailProfile)
                        viewModel.closeProfileDetail()
                    },
                    onPass = {
                        viewModel.swipeLeft(detailProfile)
                        viewModel.closeProfileDetail()
                    }
                )
            }

            // Filter Bottom Sheet
            if (showFilterSheet) {
                FilterBottomSheet(
                    currentFilters = uiState.filters,
                    language = uiState.language,
                    onDismiss = { showFilterSheet = false },
                    onApply = { newFilters ->
                        viewModel.updateFilters(newFilters)
                    }
                )
            }
        }
    }
}
