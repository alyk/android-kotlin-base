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
import com.example.core.model.Genre
import com.example.core.ui.theme.GenreAction
import com.example.core.ui.theme.GenreAdventure
import com.example.core.ui.theme.GenreIndie
import com.example.core.ui.theme.GenrePuzzle
import com.example.core.ui.theme.GenreRPG
import com.example.core.ui.theme.GenreSimulation
import com.example.core.ui.theme.GenreSports
import com.example.core.ui.theme.GenreStrategy

/**
 * A chip component displaying game genre with color coding.
 */
@Composable
fun GenreChip(
    genre: Genre,
    modifier: Modifier = Modifier,
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.labelSmall
) {
    val backgroundColor = getGenreColor(genre)
    
    Box(
        modifier = modifier
            .background(
                color = backgroundColor.copy(alpha = 0.9f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = genre.displayName(),
            style = textStyle,
            color = Color.White
        )
    }
}

/**
 * Returns the color associated with a genre
 */
fun getGenreColor(genre: Genre): Color {
    return when (genre) {
        Genre.ACTION -> GenreAction
        Genre.ADVENTURE -> GenreAdventure
        Genre.RPG -> GenreRPG
        Genre.STRATEGY -> GenreStrategy
        Genre.SIMULATION -> GenreSimulation
        Genre.SPORTS -> GenreSports
        Genre.PUZZLE -> GenrePuzzle
        Genre.INDIE -> GenreIndie
        Genre.OTHER -> MaterialTheme.colorScheme.primary
    }
}

/**
 * Extension function to get display name for genre
 */
fun Genre.displayName(): String {
    return when (this) {
        Genre.ACTION -> "Action"
        Genre.ADVENTURE -> "Adventure"
        Genre.RPG -> "RPG"
        Genre.STRATEGY -> "Strategy"
        Genre.SIMULATION -> "Simulation"
        Genre.SPORTS -> "Sports"
        Genre.PUZZLE -> "Puzzle"
        Genre.INDIE -> "Indie"
        Genre.OTHER -> "Other"
    }
}

/**
 * Chip for multiple genre selection
 */
@Composable
fun GenreFilterChip(
    genre: Genre,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) {
        getGenreColor(genre)
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
                text = genre.displayName(),
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