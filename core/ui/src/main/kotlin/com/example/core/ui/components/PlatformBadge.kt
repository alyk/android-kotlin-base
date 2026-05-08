package com.example.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core.model.Platform
import com.example.core.ui.theme.PlatformCrossPlatform
import com.example.core.ui.theme.PlatformMobile
import com.example.core.ui.theme.PlatformNintendo
import com.example.core.ui.theme.PlatformPC
import com.example.core.ui.theme.PlatformPlayStation
import com.example.core.ui.theme.PlatformXbox

/**
 * A badge component displaying game platform with color coding.
 */
@Composable
fun PlatformBadge(
    platform: Platform,
    modifier: Modifier = Modifier,
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.labelSmall
) {
    val backgroundColor = getPlatformColor(platform)
    
    Box(
        modifier = modifier
            .background(
                color = backgroundColor.copy(alpha = 0.9f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = platform.displayName(),
            style = textStyle,
            color = Color.White
        )
    }
}

/**
 * Returns the color associated with a platform
 */
@Composable
fun getPlatformColor(platform: Platform): Color {
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    return when (platform) {
        Platform.PC -> PlatformPC
        Platform.PLAYSTATION -> PlatformPlayStation
        Platform.XBOX -> PlatformXbox
        Platform.NINTENDO -> PlatformNintendo
        Platform.MOBILE -> PlatformMobile
        Platform.WEB -> tertiaryColor
        Platform.CROSSPLATFORM -> PlatformCrossPlatform
        Platform.OTHER -> secondaryColor
    }
}

/**
 * Extension function to get display name for platform
 */
@Composable
fun Platform.displayName(): String {
    return when (this) {
        Platform.PC -> "PC"
        Platform.PLAYSTATION -> "PlayStation"
        Platform.XBOX -> "Xbox"
        Platform.NINTENDO -> "Nintendo"
        Platform.MOBILE -> "Mobile"
        Platform.WEB -> "Web"
        Platform.CROSSPLATFORM -> "Cross-Platform"
        Platform.OTHER -> "Other"
    }
}

/**
 * Icon representation for platform
 */
@Composable
fun PlatformIcon(
    platform: Platform,
    modifier: Modifier = Modifier,
    tint: Color? = null
) {
    val icon = when (platform) {
        Platform.PC -> "💻"
        Platform.PLAYSTATION -> "🎮"
        Platform.XBOX -> "🎯"
        Platform.NINTENDO -> "🍀"
        Platform.MOBILE -> "📱"
        Platform.WEB -> "🌐"
        Platform.CROSSPLATFORM -> "🔄"
        Platform.OTHER -> "🎲"
    }

    val finalTint = tint ?: getPlatformColor(platform)

    Text(
        text = icon,
        modifier = modifier
    )
}

/**
 * Filter chip for platform selection
 */
@Composable
fun PlatformFilterChip(
    platform: Platform,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) {
        getPlatformColor(platform)
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    
    val textColor = if (isSelected) {
        Color.White
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    androidx.compose.material3.FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Text(
                text = platform.displayName(),
                color = textColor
            )
        },
        modifier = modifier,
        colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            selectedContainerColor = backgroundColor
        )
    )
}