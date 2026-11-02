package com.example.model

data class ProfilePrompt(
    val question: String,
    val questionSi: String,
    val answer: String
)

data class DatingProfile(
    val id: String,
    val name: String,
    val age: Int,
    val occupation: String,
    val occupationSi: String,
    val location: String,
    val distanceKm: Int,
    val bio: String,
    val bioSi: String,
    val photos: List<String>,
    val interests: List<String>,
    val interestsSi: List<String>,
    val prompts: List<ProfilePrompt>,
    val isVerified: Boolean = true,
    val compatibility: Int = 92,
    val height: String = "5' 6\"",
    val zodiac: String = "Leo ♌",
    val lookingFor: String = "Long-term relationship",
    val lookingForSi: String = "ස්ථීර සබඳතාවක්",
    val education: String = "University Graduate"
)

data class ChatMessage(
    val id: String,
    val senderId: String,
    val text: String,
    val timestamp: String,
    val isFromMe: Boolean,
    val giftType: String? = null
)

data class MatchItem(
    val id: String,
    val profile: DatingProfile,
    val matchedAt: String,
    val lastMessage: String?,
    val lastMessageTime: String?,
    val unreadCount: Int = 0,
    val isOnline: Boolean = true
)

data class FilterOptions(
    val genderPreference: String = "Everyone",
    val minAge: Int = 20,
    val maxAge: Int = 35,
    val maxDistanceKm: Int = 50,
    val onlyVerified: Boolean = false,
    val selectedCity: String = "All"
)

data class UserProfile(
    val name: String = "Tharindu",
    val age: Int = 26,
    val occupation: String = "Software Engineer",
    val location: String = "Colombo, Sri Lanka",
    val bio: String = "Love coffee, beach sunsets, photography & tech. Looking for someone to explore hidden gems around the island! 🌊☕",
    val photoUrl: String = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=800&q=80",
    val interests: List<String> = listOf("Travel", "Coffee", "Photography", "Music", "Road Trips"),
    val completionPercentage: Int = 85
)

enum class AppLanguage {
    SINHALA,
    ENGLISH
}
