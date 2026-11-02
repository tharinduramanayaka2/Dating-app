package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.model.AppLanguage
import com.example.model.DatingProfile
import com.example.model.FilterOptions
import com.example.model.UserProfile
import com.example.ui.theme.CoralAccent
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.GoldSuperLike
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RoseLight
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.VerifiedBlue

@Composable
fun MatchCelebrationDialog(
    matchedProfile: DatingProfile,
    userProfile: UserProfile,
    language: AppLanguage,
    onSendMessage: () -> Unit,
    onKeepSwiping: () -> Unit
) {
    val isSinhala = language == AppLanguage.SINHALA
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heartPulse"
    )

    Dialog(
        onDismissRequest = onKeepSwiping,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            DarkBackground.copy(alpha = 0.95f),
                            RoseDark.copy(alpha = 0.9f),
                            DarkBackground.copy(alpha = 0.98f)
                        )
                    )
                )
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Animated Sparkle Icon
                Box(
                    modifier = Modifier
                        .scale(pulseScale)
                        .size(80.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(RoseLight, RosePrimary, Color.Transparent)
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Match Heart",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (isSinhala) "නව ගැළපීමක්! 💖" else "It's a Match! 💖",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = if (isSinhala)
                        "ඔබ සහ ${matchedProfile.name} එකිනෙකාට කැමති වී ඇත"
                    else
                        "You and ${matchedProfile.name} liked each other!",
                    fontSize = 15.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp, bottom = 28.dp)
                )

                // Overlapping Avatars
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // User Avatar
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .border(3.dp, RoseLight, CircleShape)
                    ) {
                        AsyncImage(
                            model = userProfile.photoUrl,
                            contentDescription = userProfile.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.width(-20.dp))

                    // Matched Profile Avatar
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .border(3.dp, GoldSuperLike, CircleShape)
                    ) {
                        AsyncImage(
                            model = matchedProfile.photos.firstOrNull(),
                            contentDescription = matchedProfile.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                // Send message button
                Button(
                    onClick = onSendMessage,
                    colors = ButtonDefaults.buttonColors(containerColor = RosePrimary),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(52.dp)
                        .testTag("send_match_message_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isSinhala) "කතාබස් අරඹන්න" else "Send a Message",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Keep Swiping Button
                OutlinedButton(
                    onClick = onKeepSwiping,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(listOf(RoseLight, CoralAccent))
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(48.dp)
                        .testTag("keep_swiping_button")
                ) {
                    Text(
                        text = if (isSinhala) "තව අය සොයන්න" else "Keep Swiping",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterBottomSheet(
    currentFilters: FilterOptions,
    language: AppLanguage,
    onDismiss: () -> Unit,
    onApply: (FilterOptions) -> Unit
) {
    val isSinhala = language == AppLanguage.SINHALA
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var distanceKm by remember { mutableFloatStateOf(currentFilters.maxDistanceKm.toFloat()) }
    var ageRange by remember {
        mutableStateOf(currentFilters.minAge.toFloat()..currentFilters.maxAge.toFloat())
    }
    var selectedCity by remember { mutableStateOf(currentFilters.selectedCity) }
    var onlyVerified by remember { mutableStateOf(currentFilters.onlyVerified) }
    var genderPref by remember { mutableStateOf(currentFilters.genderPreference) }

    val cities = listOf("All", "Colombo", "Kandy", "Galle", "Negombo", "Gampaha")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isSinhala) "සෙවුම් පෙරහන්" else "Discovery Filters",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Maximum Distance Slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isSinhala) "උපරිම දුර" else "Maximum Distance",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${distanceKm.toInt()} km",
                    fontWeight = FontWeight.Bold,
                    color = RosePrimary
                )
            }
            Slider(
                value = distanceKm,
                onValueChange = { distanceKm = it },
                valueRange = 5f..100f,
                steps = 18,
                modifier = Modifier.testTag("filter_distance_slider")
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Age Range Slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isSinhala) "වයස් පරාසය" else "Age Range",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${ageRange.start.toInt()} - ${ageRange.endInclusive.toInt()}",
                    fontWeight = FontWeight.Bold,
                    color = RosePrimary
                )
            }
            RangeSlider(
                value = ageRange,
                onValueChange = { ageRange = it },
                valueRange = 18f..50f,
                steps = 31,
                modifier = Modifier.testTag("filter_age_slider")
            )

            Spacer(modifier = Modifier.height(16.dp))

            // City chips
            Text(
                text = if (isSinhala) "නගරය / ප්‍රදේශය" else "Location / City",
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                cities.forEach { city ->
                    val isSelected = selectedCity == city
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCity = city },
                        label = { Text(city) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = RosePrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Only Verified Profiles toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (isSinhala) "තහවුරු කළ ගිණුම් පමණි" else "Verified Profiles Only",
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = if (isSinhala) "නිල් සලකුණ සහිත ගිණුම් පමණක් පෙන්වන්න" else "Show only profiles with blue checkmark",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = onlyVerified,
                    onCheckedChange = { onlyVerified = it },
                    modifier = Modifier.testTag("filter_verified_switch")
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Apply Button
            Button(
                onClick = {
                    onApply(
                        currentFilters.copy(
                            maxDistanceKm = distanceKm.toInt(),
                            minAge = ageRange.start.toInt(),
                            maxAge = ageRange.endInclusive.toInt(),
                            selectedCity = selectedCity,
                            onlyVerified = onlyVerified,
                            genderPreference = genderPref
                        )
                    )
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = RosePrimary),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("apply_filter_button")
            ) {
                Text(
                    text = if (isSinhala) "පෙරහන් යොදන්න" else "Apply Filters",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileDetailBottomSheet(
    profile: DatingProfile,
    language: AppLanguage,
    onDismiss: () -> Unit,
    onLike: () -> Unit,
    onPass: () -> Unit
) {
    val isSinhala = language == AppLanguage.SINHALA
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var currentPhotoIndex by remember { mutableStateOf(0) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            // Photo Gallery Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
            ) {
                AsyncImage(
                    model = profile.photos.getOrElse(currentPhotoIndex) { profile.photos.first() },
                    contentDescription = profile.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Multi photo tap areas (left half = prev, right half = next)
                Row(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .clickable {
                                if (currentPhotoIndex > 0) currentPhotoIndex--
                            }
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .clickable {
                                if (currentPhotoIndex < profile.photos.size - 1) currentPhotoIndex++
                            }
                    )
                }

                // Dot Indicators
                Row(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    profile.photos.indices.forEach { index ->
                        Box(
                            modifier = Modifier
                                .height(4.dp)
                                .width(if (index == currentPhotoIndex) 24.dp else 12.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(
                                    if (index == currentPhotoIndex) Color.White else Color.White.copy(alpha = 0.5f)
                                )
                        )
                    }
                }

                // Close Button
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }

            // Profile info content
            Column(modifier = Modifier.padding(20.dp)) {
                // Name & Age & Verified
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${profile.name}, ${profile.age}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (profile.isVerified) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Verified",
                            tint = VerifiedBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Job & Location
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Work,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isSinhala) profile.occupationSi else profile.occupation,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = RosePrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${profile.location} • ${profile.distanceKm} km away",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Bio
                Text(
                    text = if (isSinhala) "මා ගැන" else "About Me",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = if (isSinhala) profile.bioSi else profile.bio,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 6.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Key Profile Specs Chips
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SpecChip(icon = Icons.Default.Height, text = profile.height)
                    SpecChip(icon = Icons.Default.Star, text = profile.zodiac)
                    SpecChip(icon = Icons.Default.Favorite, text = if (isSinhala) profile.lookingForSi else profile.lookingFor)
                    SpecChip(icon = Icons.Default.School, text = profile.education)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Interest tags
                Text(
                    text = if (isSinhala) "කැමති දේවල්" else "Interests & Passions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val interests = if (isSinhala) profile.interestsSi else profile.interests
                    interests.forEach { tag ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = tag,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Prompts
                profile.prompts.forEach { prompt ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (isSinhala) prompt.questionSi else prompt.question,
                                style = MaterialTheme.typography.bodySmall,
                                color = RosePrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = prompt.answer,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action buttons (Pass & Like)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            onPass()
                            onDismiss()
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Pass")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isSinhala) "මඟහරින්න" else "Pass")
                    }

                    Button(
                        onClick = {
                            onLike()
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = RosePrimary),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Like")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isSinhala) "කැමතියි" else "Like")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun SpecChip(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
