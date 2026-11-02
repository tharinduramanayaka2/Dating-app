package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SampleData
import com.example.model.AppLanguage
import com.example.model.ChatMessage
import com.example.model.DatingProfile
import com.example.model.FilterOptions
import com.example.model.MatchItem
import com.example.model.UserProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class DatingUiState(
    val deck: List<DatingProfile> = emptyList(),
    val swipedHistory: List<DatingProfile> = emptyList(),
    val matches: List<MatchItem> = emptyList(),
    val chatMessages: Map<String, List<ChatMessage>> = emptyMap(),
    val activeChatMatch: MatchItem? = null,
    val newMatchProfile: DatingProfile? = null,
    val selectedDetailProfile: DatingProfile? = null,
    val filters: FilterOptions = FilterOptions(),
    val language: AppLanguage = AppLanguage.SINHALA, // Default to Sinhala per user request!
    val userProfile: UserProfile = UserProfile(),
    val topPicks: List<DatingProfile> = emptyList(),
    val likesReceived: List<DatingProfile> = emptyList(),
    val likesCount: Int = 14
)

class DatingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DatingUiState())
    val uiState: StateFlow<DatingUiState> = _uiState.asStateFlow()

    init {
        val initialDeck = SampleData.sampleProfiles
        _uiState.update { state ->
            state.copy(
                deck = initialDeck,
                matches = SampleData.initialMatches,
                chatMessages = SampleData.starterMessages,
                topPicks = initialDeck.take(4),
                likesReceived = listOf(
                    SampleData.sampleProfiles[2],
                    SampleData.sampleProfiles[3],
                    SampleData.sampleProfiles[5]
                )
            )
        }
    }

    fun toggleLanguage() {
        _uiState.update { current ->
            val nextLang = if (current.language == AppLanguage.SINHALA) {
                AppLanguage.ENGLISH
            } else {
                AppLanguage.SINHALA
            }
            current.copy(language = nextLang)
        }
    }

    fun swipeRight(profile: DatingProfile) {
        val isMatch = true // Delightful match feedback
        _uiState.update { current ->
            val updatedDeck = current.deck.filter { it.id != profile.id }
            val updatedHistory = current.swipedHistory + profile
            
            val updatedMatches = if (current.matches.none { it.profile.id == profile.id }) {
                val newMatch = MatchItem(
                    id = "match_${profile.id}",
                    profile = profile,
                    matchedAt = "Just now",
                    lastMessage = if (current.language == AppLanguage.SINHALA) "නව ගැළපීමක්! කතාබස් අරඹන්න ✨" else "New Match! Say hello ✨",
                    lastMessageTime = "Now",
                    unreadCount = 1,
                    isOnline = true
                )
                listOf(newMatch) + current.matches
            } else {
                current.matches
            }

            current.copy(
                deck = updatedDeck,
                swipedHistory = updatedHistory,
                matches = updatedMatches,
                newMatchProfile = if (isMatch) profile else null
            )
        }
    }

    fun swipeLeft(profile: DatingProfile) {
        _uiState.update { current ->
            current.copy(
                deck = current.deck.filter { it.id != profile.id },
                swipedHistory = current.swipedHistory + profile
            )
        }
    }

    fun superLike(profile: DatingProfile) {
        _uiState.update { current ->
            val updatedDeck = current.deck.filter { it.id != profile.id }
            val newMatch = MatchItem(
                id = "match_${profile.id}",
                profile = profile,
                matchedAt = "Just now",
                lastMessage = if (current.language == AppLanguage.SINHALA) "සුපිරි කැමැත්තක් යැව්වා! ⭐" else "You sent a Super Like! ⭐",
                lastMessageTime = "Now",
                unreadCount = 1,
                isOnline = true
            )
            val updatedMatches = if (current.matches.none { it.profile.id == profile.id }) {
                listOf(newMatch) + current.matches
            } else {
                current.matches
            }

            current.copy(
                deck = updatedDeck,
                swipedHistory = current.swipedHistory + profile,
                matches = updatedMatches,
                newMatchProfile = profile
            )
        }
    }

    fun rewind() {
        val lastSwiped = _uiState.value.swipedHistory.lastOrNull() ?: return
        _uiState.update { current ->
            val updatedHistory = current.swipedHistory.dropLast(1)
            val updatedDeck = listOf(lastSwiped) + current.deck.filter { it.id != lastSwiped.id }
            current.copy(
                deck = updatedDeck,
                swipedHistory = updatedHistory
            )
        }
    }

    fun reloadDeck() {
        _uiState.update { current ->
            current.copy(
                deck = SampleData.sampleProfiles,
                swipedHistory = emptyList()
            )
        }
    }

    fun dismissMatchDialog() {
        _uiState.update { it.copy(newMatchProfile = null) }
    }

    fun openProfileDetail(profile: DatingProfile) {
        _uiState.update { it.copy(selectedDetailProfile = profile) }
    }

    fun closeProfileDetail() {
        _uiState.update { it.copy(selectedDetailProfile = null) }
    }

    fun openChat(match: MatchItem) {
        _uiState.update { current ->
            // Clear unread
            val updatedMatches = current.matches.map {
                if (it.id == match.id) it.copy(unreadCount = 0) else it
            }
            current.copy(
                activeChatMatch = match,
                matches = updatedMatches
            )
        }
    }

    fun closeChat() {
        _uiState.update { it.copy(activeChatMatch = null) }
    }

    fun sendMessage(matchId: String, text: String, giftType: String? = null) {
        val timeNow = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
        val newMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            senderId = "user",
            text = text,
            timestamp = timeNow,
            isFromMe = true,
            giftType = giftType
        )

        _uiState.update { current ->
            val currentList = current.chatMessages[matchId] ?: emptyList()
            val updatedMessages = current.chatMessages + (matchId to (currentList + newMessage))
            val updatedMatches = current.matches.map {
                if (it.id == matchId) {
                    it.copy(
                        lastMessage = if (giftType != null) "Sent a gift: $giftType" else text,
                        lastMessageTime = timeNow
                    )
                } else it
            }
            current.copy(
                chatMessages = updatedMessages,
                matches = updatedMatches
            )
        }

        // Simulate cute match reply after 1.2s
        viewModelScope.launch {
            delay(1200)
            val isSinhala = _uiState.value.language == AppLanguage.SINHALA
            val replyText = when {
                giftType == "rose" -> if (isSinhala) "අනේ ගොඩක් ස්තූතියි රෝස මලට! 🌹 ඒක හරිම ලස්සනයි!" else "Aww thank you so much for the rose! 🌹 That made my day!"
                giftType == "chocolate" -> if (isSinhala) "චොකලට් වලට මම හරිම ආසයි! 🍫😋" else "Chocolates! You know the way to my heart 🍫😋"
                giftType == "coffee" -> if (isSinhala) "කෝපි බොන්න අපි දවසක යමුද? ☕" else "We should totally grab iced coffee together sometime! ☕"
                text.contains("?") -> if (isSinhala) "ඔව් අනිවාර්යයෙන්ම! ඔයාට කොහොමද? 😊" else "Yes, absolutely! How about you? 😊"
                else -> if (isSinhala) "හායි! ඔයාගෙ මැසේජ් එක දැකලා මට ගොඩක් සතුටු හිතුනා ✨" else "Hey! So glad we connected here, loving your vibe ✨"
            }
            val replyTime = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
            val replyMsg = ChatMessage(
                id = UUID.randomUUID().toString(),
                senderId = "match",
                text = replyText,
                timestamp = replyTime,
                isFromMe = false
            )

            _uiState.update { current ->
                val currentList = current.chatMessages[matchId] ?: emptyList()
                val updatedMessages = current.chatMessages + (matchId to (currentList + replyMsg))
                val updatedMatches = current.matches.map {
                    if (it.id == matchId) {
                        it.copy(lastMessage = replyText, lastMessageTime = replyTime)
                    } else it
                }
                current.copy(
                    chatMessages = updatedMessages,
                    matches = updatedMatches
                )
            }
        }
    }

    fun updateFilters(newFilters: FilterOptions) {
        _uiState.update { current ->
            // Filter profiles based on new filters
            val filtered = SampleData.sampleProfiles.filter { profile ->
                val cityMatch = newFilters.selectedCity == "All" || profile.location.contains(newFilters.selectedCity, ignoreCase = true)
                val ageMatch = profile.age in newFilters.minAge..newFilters.maxAge
                val verifiedMatch = !newFilters.onlyVerified || profile.isVerified
                cityMatch && ageMatch && verifiedMatch
            }
            current.copy(
                filters = newFilters,
                deck = if (filtered.isEmpty()) SampleData.sampleProfiles else filtered
            )
        }
    }

    fun updateUserBio(newBio: String) {
        _uiState.update { it.copy(userProfile = it.userProfile.copy(bio = newBio)) }
    }
}
