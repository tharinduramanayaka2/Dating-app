package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.AppLanguage
import com.example.model.DatingProfile
import com.example.ui.theme.CoralAccent
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.GoldSuperLike
import com.example.ui.theme.LikeGreen
import com.example.ui.theme.PassGray
import com.example.ui.theme.PurpleGradientStart
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RoseLight
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.VerifiedBlue
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun DiscoverScreen(
    deck: List<DatingProfile>,
    canRewind: Boolean,
    language: AppLanguage,
    likesCount: Int,
    onSwipeRight: (DatingProfile) -> Unit,
    onSwipeLeft: (DatingProfile) -> Unit,
    onSuperLike: (DatingProfile) -> Unit,
    onRewind: () -> Unit,
    onReloadDeck: () -> Unit,
    onOpenFilters: () -> Unit,
    onToggleLanguage: () -> Unit,
    onOpenProfileDetail: (DatingProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSinhala = language == AppLanguage.SINHALA
    val topProfile = deck.firstOrNull()
    val nextProfile = deck.getOrNull(1)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Top App Bar / Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // App Branding Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.testTag("app_brand_logo")
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            Brush.linearGradient(listOf(RosePrimary, RoseLight)),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Logo",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = if (isSinhala) "ආදරේ" else "Spark",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = RosePrimary
                    )
                    Text(
                        text = if (isSinhala) "ගැළපෙන ආදරය සොයන්න" else "Match & Date",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Right Action Controls: Language Toggle & Filter
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Language Toggle Badge
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .clickable { onToggleLanguage() }
                        .testTag("toggle_language_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isSinhala) "සිංහල 🇱🇰" else "English 🌐",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                // Filter Button
                IconButton(
                    onClick = onOpenFilters,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                            CircleShape
                        )
                        .testTag("filter_icon_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Filters",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Center: Card Deck
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (deck.isEmpty()) {
                // Empty State
                EmptyDeckView(
                    isSinhala = isSinhala,
                    onReloadDeck = onReloadDeck
                )
            } else {
                // Next card underneath for depth
                if (nextProfile != null) {
                    ProfileCard(
                        profile = nextProfile,
                        isSinhala = isSinhala,
                        onOpenDetail = { onOpenProfileDetail(nextProfile) },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp)
                            .scale(0.95f)
                            .offset(y = 12.dp)
                    )
                }

                // Top active swipable card
                if (topProfile != null) {
                    SwipableProfileCard(
                        profile = topProfile,
                        isSinhala = isSinhala,
                        onSwipeRight = { onSwipeRight(topProfile) },
                        onSwipeLeft = { onSwipeLeft(topProfile) },
                        onSuperLike = { onSuperLike(topProfile) },
                        onOpenDetail = { onOpenProfileDetail(topProfile) },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Bottom Action Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rewind Button
            ActionRoundButton(
                icon = Icons.Default.Refresh,
                tint = GoldSuperLike,
                size = 46.dp,
                iconSize = 22.dp,
                enabled = canRewind,
                testTag = "action_rewind_button",
                onClick = onRewind
            )

            // Pass Button
            ActionRoundButton(
                icon = Icons.Default.Close,
                tint = RosePrimary,
                size = 56.dp,
                iconSize = 28.dp,
                enabled = topProfile != null,
                testTag = "action_pass_button",
                onClick = { topProfile?.let { onSwipeLeft(it) } }
            )

            // Super Like Button
            ActionRoundButton(
                icon = Icons.Default.Star,
                tint = GoldSuperLike,
                size = 50.dp,
                iconSize = 24.dp,
                enabled = topProfile != null,
                testTag = "action_super_like_button",
                onClick = { topProfile?.let { onSuperLike(it) } }
            )

            // Like Button
            ActionRoundButton(
                icon = Icons.Default.Favorite,
                tint = LikeGreen,
                size = 58.dp,
                iconSize = 30.dp,
                enabled = topProfile != null,
                testTag = "action_like_button",
                onClick = { topProfile?.let { onSwipeRight(it) } }
            )

            // Boost Sparkle Button
            var boosted by remember { mutableStateOf(false) }
            ActionRoundButton(
                icon = Icons.Default.Bolt,
                tint = PurpleGradientStart,
                size = 46.dp,
                iconSize = 22.dp,
                enabled = true,
                testTag = "action_boost_button",
                onClick = { boosted = true }
            )
        }
    }
}

@Composable
fun SwipableProfileCard(
    profile: DatingProfile,
    isSinhala: Boolean,
    onSwipeRight: () -> Unit,
    onSwipeLeft: () -> Unit,
    onSuperLike: () -> Unit,
    onOpenDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    BoxWithConstraints(modifier = modifier) {
        val swipeThreshold = constraints.maxWidth * 0.35f
        val rotation = (offsetX.value / 30f).coerceIn(-25f, 25f)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
                .rotate(rotation)
                .pointerInput(profile.id) {
                    detectDragGestures(
                        onDragEnd = {
                            coroutineScope.launch {
                                when {
                                    offsetX.value > swipeThreshold -> {
                                        offsetX.animateTo(1200f, tween(250))
                                        onSwipeRight()
                                    }
                                    offsetX.value < -swipeThreshold -> {
                                        offsetX.animateTo(-1200f, tween(250))
                                        onSwipeLeft()
                                    }
                                    offsetY.value < -swipeThreshold -> {
                                        offsetY.animateTo(-1200f, tween(250))
                                        onSuperLike()
                                    }
                                    else -> {
                                        offsetX.animateTo(0f, spring(stiffness = Spring.StiffnessMediumLow))
                                        offsetY.animateTo(0f, spring(stiffness = Spring.StiffnessMediumLow))
                                    }
                                }
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            coroutineScope.launch {
                                offsetX.snapTo(offsetX.value + dragAmount.x)
                                offsetY.snapTo(offsetY.value + dragAmount.y)
                            }
                        }
                    )
                }
        ) {
            ProfileCard(
                profile = profile,
                isSinhala = isSinhala,
                onOpenDetail = onOpenDetail,
                modifier = Modifier.fillMaxSize()
            )

            // Swipe indicator stamps overlay
            // LIKE stamp (green)
            if (offsetX.value > 60f) {
                val alpha = (offsetX.value / swipeThreshold).coerceIn(0f, 1f)
                Box(
                    modifier = Modifier
                        .padding(24.dp)
                        .align(Alignment.TopStart)
                        .rotate(-15f)
                        .border(4.dp, LikeGreen.copy(alpha = alpha), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (isSinhala) "කැමතියි" else "LIKE",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = LikeGreen.copy(alpha = alpha)
                    )
                }
            }

            // NOPE stamp (red)
            if (offsetX.value < -60f) {
                val alpha = (-offsetX.value / swipeThreshold).coerceIn(0f, 1f)
                Box(
                    modifier = Modifier
                        .padding(24.dp)
                        .align(Alignment.TopEnd)
                        .rotate(15f)
                        .border(4.dp, RosePrimary.copy(alpha = alpha), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (isSinhala) "මඟහරින්න" else "NOPE",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = RosePrimary.copy(alpha = alpha)
                    )
                }
            }

            // SUPER LIKE stamp (gold)
            if (offsetY.value < -60f) {
                val alpha = (-offsetY.value / swipeThreshold).coerceIn(0f, 1f)
                Box(
                    modifier = Modifier
                        .padding(24.dp)
                        .align(Alignment.TopCenter)
                        .border(4.dp, GoldSuperLike.copy(alpha = alpha), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (isSinhala) "සුපිරි කැමැත්තක්" else "SUPER LIKE",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = GoldSuperLike.copy(alpha = alpha)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileCard(
    profile: DatingProfile,
    isSinhala: Boolean,
    onOpenDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    var photoIndex by remember { mutableIntStateOf(0) }

    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .testTag("dating_profile_card_${profile.id}")
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Main image
            AsyncImage(
                model = profile.photos.getOrElse(photoIndex) { profile.photos.first() },
                contentDescription = profile.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Tap left/right to browse profile photos
            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clickable {
                            if (photoIndex > 0) photoIndex--
                        }
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clickable {
                            if (photoIndex < profile.photos.size - 1) photoIndex++
                        }
                )
            }

            // Photo count indicators at top
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                profile.photos.indices.forEach { index ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                if (index == photoIndex) Color.White else Color.White.copy(alpha = 0.45f)
                            )
                    )
                }
            }

            // Compatibility Pill Badge (e.g. 98% Match)
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.Black.copy(alpha = 0.65f),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 26.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = RosePrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "${profile.compatibility}% ${if (isSinhala) "ගැළපීමක්" else "Match"}",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Bottom Gradient Scrim & Info
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.5f),
                                Color.Black.copy(alpha = 0.88f),
                                Color.Black.copy(alpha = 0.96f)
                            )
                        )
                    )
                    .padding(horizontal = 18.dp, vertical = 18.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Name, Age & Verified & Info Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "${profile.name}, ${profile.age}",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            if (profile.isVerified) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Verified",
                                    tint = VerifiedBlue,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }

                        // Info Button to view full details
                        IconButton(
                            onClick = onOpenDetail,
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color.White.copy(alpha = 0.25f), CircleShape)
                                .testTag("open_profile_info_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "View Profile",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Occupation & Location
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Work,
                            contentDescription = null,
                            tint = RoseLight,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isSinhala) profile.occupationSi else profile.occupation,
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 14.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = CoralAccent,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${profile.location} • ${profile.distanceKm} km away",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 13.sp
                        )
                    }

                    // Bio preview
                    Text(
                        text = if (isSinhala) profile.bioSi else profile.bio,
                        color = Color.White.copy(alpha = 0.95f),
                        fontSize = 14.sp,
                        maxLines = 2,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    // Interest chips
                    Spacer(modifier = Modifier.height(10.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val tags = if (isSinhala) profile.interestsSi.take(3) else profile.interests.take(3)
                        tags.forEach { tag ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = tag,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyDeckView(
    isSinhala: Boolean,
    onReloadDeck: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .background(
                        Brush.radialGradient(listOf(RoseLight.copy(alpha = 0.5f), Color.Transparent)),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = RosePrimary,
                    modifier = Modifier.size(52.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (isSinhala) "අවට සියලු දෙනා බලා අවසන්! ✨" else "You've Seen Everyone Nearby! ✨",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = if (isSinhala)
                    "නව ගැළපීම් සොයා ගැනීමට පෙරහන් වෙනස් කරන්න හෝ නැවත මුල සිට බලන්න."
                else
                    "Expand your filters or reload to see profiles again.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            Surface(
                shape = RoundedCornerShape(24.dp),
                color = RosePrimary,
                modifier = Modifier
                    .clickable { onReloadDeck() }
                    .testTag("reload_deck_button")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reload",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isSinhala) "නැවත බලන්න" else "Reload Profiles",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ActionRoundButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    size: androidx.compose.ui.unit.Dp,
    iconSize: androidx.compose.ui.unit.Dp,
    enabled: Boolean,
    testTag: String,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 6.dp,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .clickable(enabled = enabled) { onClick() }
            .testTag(testTag),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (enabled) tint else Color.Gray.copy(alpha = 0.4f),
                modifier = Modifier.size(iconSize)
            )
        }
    }
}
