---
id: task-7
title: Implement FavouritesScreen UI
status: Done
priority: high
milestone: Favourites Feature
assignee: []
created_date: '2026-05-08 18:56'
updated_date: '2026-05-08 21:01'
labels:
  - ui
  - compose
  - screen
dependencies: []
references: []
documentation: []
ordinal: 1000
---

Implement FavouritesScreen with LazyVerticalGrid showing favourited games. Use GameCard component from core:ui. Add empty state and loading states.

## Implementation Notes

Checking GameCard component requirements: GameCard expects Game type with developer field

LocalFavourite has: gameId, title, thumbnailUrl, genre, platform, rating, savedAt (no developer field)

Creating LocalFavourite to Game conversion extension function

Updating FavouritesScreen to use GameCard from core:ui

## Final Summary

FavouritesScreen UI has been successfully implemented with:

1. **LocalFavouriteExtensions.kt** (NEW FILE) - Created conversion extension function `LocalFavourite.toGame()` to convert LocalFavourite to Game type for use with GameCard component. Includes helper functions for genre and platform string-to-enum conversions.

2. **FavouritesScreen.kt** (UPDATED) - Refactored to use GameCard from core:ui:
   - LazyVerticalGrid displaying favourited games
   - Loading state with CircularProgressIndicator
   - Empty state with "No Favourites Yet" message
   - Error state using ErrorScreen component from core:ui
   - Side effects handling (navigation, snackbars)
   - Uses `favourite.toGame()` to convert LocalFavourite to Game for GameCard

**Key Changes:**
- Removed custom FavouriteCard, GenreChip, RatingBadge, PlatformBadge, RatingBar components (now using GameCard)
- Added LazyVerticalGrid with GridCells.Adaptive(minSize = 160.dp) for responsive grid layout
- GameCard is configured with `isFavourited = true` and `onFavouriteClick = null` (favourite button disabled)
- Grid uses proper contentPadding and spacing for visual layout

**Build Status:** ✅ BUILD SUCCESSFUL - All compilation checks passed
