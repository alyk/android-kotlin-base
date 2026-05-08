package com.example.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        
        // Half star
        if (hasHalfStar) {
            Icon(
                imageVector = Icons.Filled.StarHalf,
                contentDescription = null,
                tint = activeColor,
                modifier = Modifier.size(starSize)
            )
        }
        
        // Empty stars
        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Filled.StarBorder,
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
    androidx.compose.material3.Surface(
        modifier = modifier,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
        color = backgroundColor
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
            androidx.compose.foundation.layout.Spacer(
                modifier = Modifier.size(4.dp)
            )
            androidx.compose.material3.Text(
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
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyMedium
) {
    val color = when {
        rating >= 4.5f -> RatingExcellent
        rating >= 3.5f -> RatingGood
        rating >= 2.5f -> RatingAverage
        else -> RatingPoor
    }
    
    androidx.compose.foundation.layout.Row(
        modifier = modifier,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp)
        )
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(4.dp))
        androidx.compose.material3.Text(
            text = String.format("%.1f", rating),
            style = textStyle,
            color = color
        )
    }
}