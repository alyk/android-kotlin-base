package com.example.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.RatingAverage
import com.example.core.ui.theme.RatingExcellent
import com.example.core.ui.theme.RatingGood
import com.example.core.ui.theme.RatingPoor

/**
 * A rating bar component displaying stars based on game rating.
 * Shows filled stars, half stars, and empty stars based on rating value.
 */
@Composable
fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier,
    maxRating: Int = 5,
    starSize: Dp = 16.dp,
    activeColor: Color = RatingExcellent,
    inactiveColor: Color = MaterialTheme.colorScheme.outline
) {
    Row(modifier = modifier) {
        val fullStars = rating.toInt()
        val hasHalfStar = rating - fullStars >= 0.5f
        val emptyStars = maxRating - fullStars - if (hasHalfStar) 1 else 0

        // Filled stars
        repeat(fullStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = activeColor,
                modifier = Modifier.size(starSize)
            )
        }

        // Half star (using filled star with active color for simplicity)
        if (hasHalfStar) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = activeColor,
                modifier = Modifier.size(starSize)
            )
        }

        // Empty stars
        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = inactiveColor,
                modifier = Modifier.size(starSize)
            )
        }
    }
}

/**
 * A badge displaying numeric rating with color based on score.
 */
@Composable
fun RatingBadge(
    rating: Float,
    modifier: Modifier = Modifier
) {
    val color = when {
        rating >= 4.5f -> RatingExcellent
        rating >= 3.5f -> RatingGood
        rating >= 2.5f -> RatingAverage
        else -> RatingPoor
    }
    
    RatingBadgeContent(
        rating = rating,
        backgroundColor = color,
        modifier = modifier
    )
}

@Composable
private fun RatingBadgeContent(
    rating: Float,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = String.format("%.1f", rating),
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )
        }
    }
}

/**
 * Compact rating display for lists
 */
@Composable
fun RatingText(
    rating: Float,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    compact: Boolean = false
) {
    val color = when {
        rating >= 4.5f -> RatingExcellent
        rating >= 3.5f -> RatingGood
        rating >= 2.5f -> RatingAverage
        else -> RatingPoor
    }

    val iconSize = if (compact) 12.dp else 16.dp
    val compactTextStyle = if (compact) MaterialTheme.typography.bodySmall else textStyle

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = String.format("%.1f", rating),
            style = compactTextStyle,
            color = color
        )
    }
}