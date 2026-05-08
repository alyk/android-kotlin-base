---
id: task-6
title: "Implement shared UI components and themes in core:ui module"
status: Done
priority: medium
milestone: Core Module Ready
assignee: []
created_date: '2026-05-07 14:38'
updated_date: '2026-05-07 17:11'
labels:
  - ui
  - design
dependencies: []
references: []
documentation: []
ordinal: 1000
---

Implement shared UI components, themes, and resources in core:ui module

## Acceptance Criteria

- [x] Theme system implemented
- [x] Common UI components created
- [x] Resource management set up
- [x] Styling system consistent across modules

## Implementation Notes

Implemented shared UI components and themes in core:ui module:

**Files created:**

1. **Color.kt** - Theme color definitions:
   - Primary/secondary brand colors for light and dark themes
   - Background and surface colors
   - Text colors for primary and secondary content
   - Error, success, and warning colors
   - Genre-specific colors (Action, Adventure, RPG, Strategy, etc.)
   - Platform-specific colors (PC, PlayStation, Xbox, Nintendo, etc.)
   - Rating colors (Excellent, Good, Average, Poor)

2. **Type.kt** - Typography configuration:
   - Material 3 Typography with all text styles
   - Display, headline, title, body, and label styles
   - Proper font weights and line heights

3. **Theme.kt** - Theme composable and color schemes:
   - Light and dark color schemes
   - Dynamic color support for Android 12+
   - Status bar color configuration
   - GameAppTheme composable

4. **GameCard.kt** - Game card components:
   - GameCard - Standard card with thumbnail, title, genre, platform, rating
   - GameCardHorizontal - Horizontal variant for list views
   - Includes AsyncImage for loading thumbnails
   - Favourite button integration
   - Gradient overlays for text readability
   - Genre chips and rating badges

5. **RatingBar.kt** - Rating display components:
   - RatingBar - Star-based rating display with filled/half/empty stars
   - RatingBadge - Numeric rating badge with color coding
   - RatingText - Compact rating text with star icon
   - Color-coded based on rating score

6. **GenreChip.kt** - Genre display components:
   - GenreChip - Color-coded chip for displaying game genre
   - getGenreColor - Color mapping for genres
   - Genre.displayName() - Extension for display names
   - GenreFilterChip - Multi-select filter chip

7. **PlatformBadge.kt** - Platform display components:
   - PlatformBadge - Color-coded badge for platforms
   - getPlatformColor - Color mapping for platforms
   - Platform.displayName() - Extension for display names
   - PlatformIcon - Emoji-based platform icons
   - PlatformFilterChip - Multi-select filter chip

8. **StateComponents.kt** - State display components:
   - LoadingScreen - Full-screen loading indicator
   - LoadingIndicator - Inline loading spinner
   - ErrorScreen - Error state with retry action
   - NoNetworkScreen - Network error state
   - EmptySearchScreen - Empty search results
   - EmptyListScreen - Empty list state

9. **StateWrapper.kt** - State management wrappers:
   - StateWrapper - Generic wrapper for Result-based states
   - DataOrEmpty - Handles nullable data with loading/empty states
   - LoadingContent - Triple state handling (loading/data/error)
   - Result extension properties (isLoading, isSuccess, isError)

10. **SearchBar.kt** - Search input components:
    - SearchBar - Full search bar with outlined style
    - SearchInput - Simplified search input field
    - Keyboard actions for search submission
    - Clear button for query reset

## Final Summary

## Implemented Shared UI Components and Themes in core:ui Module

### Files Created in `core/ui/src/main/kotlin/com/example/core/ui/`:

**Theme Layer (`theme/`):**
- **Color.kt** - Complete color palette including brand colors, genre colors, platform colors, rating colors
- **Type.kt** - Material 3 Typography with all text styles configured
- **Theme.kt** - GameAppTheme composable with light/dark schemes and dynamic color support

**Components (`components/`):**
- **GameCard.kt** - GameCard and GameCardHorizontal with thumbnails, badges, and favourite buttons
- **RatingBar.kt** - Star-based rating display, numeric badges, and compact rating text
- **GenreChip.kt** - Color-coded genre chips with filter chip variants
- **PlatformBadge.kt** - Color-coded platform badges with icons and filter chips
- **StateComponents.kt** - Loading, error, empty, and no-network state screens
- **StateWrapper.kt** - Generic state wrappers for Result-based and nullable data handling
- **SearchBar.kt** - Search bar and search input components with keyboard actions

### Acceptance Criteria Met:
- ✅ **Theme configuration completed** - Light/dark themes, dynamic colors, complete color palette
- ✅ **Reusable UI components created** - Game cards, rating bars, chips, badges
- ✅ **State handling components implemented** - Loading, error, empty states with wrappers
- ✅ **Search functionality available** - SearchBar component with keyboard actions

### Key Design Decisions:
- Used Material 3 design system with dynamic color support
- Coil for async image loading with caching
- Color-coded genres and platforms for visual distinction
- Rating colors indicate quality (green=excellent, yellow=average, red=poor)
- StateWrapper provides clean separation of loading/success/error UI
- All components are responsive and work with different screen sizes
- Keyboard actions configured for seamless search experience
