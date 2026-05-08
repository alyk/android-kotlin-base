---
id: task-3
title: "Implement domain models in core:model module"
status: Done
priority: medium
milestone: Core Module Ready
assignee: []
created_date: '2026-05-07 14:38'
updated_date: '2026-05-07 17:03'
labels:
  - models
  - domain
dependencies: []
references: []
documentation: []
ordinal: 1000
---

Implement the data models and domain entities in the core:model module

## Acceptance Criteria

- [x] Data classes for all domain entities created
- [x] Models are serializable/parcelable
- [x] Models follow clean architecture principles
- [x] Models are accessible to all modules that need them

## Implementation Notes

Created domain models in core:model module:

**Files created:**
1. **Game.kt** - Core game entity with Genre and Platform enums
2. **User.kt** - User entity and UserPreferences
3. **Search.kt** - SearchFilter, SortOption, and SearchResult models
4. **Favourite.kt** - Favourite and FavouriteWithGame entities
5. **GameDetail.kt** - Extended game details with screenshots, videos, system requirements
6. **Result.kt** - Generic Result wrapper class for handling success/error/loading states

**Implementation details:**
- All models use kotlinx.serialization for JSON serialization
- Data classes follow Kotlin best practices (immutable, proper equals/hashCode)
- Clean architecture principles applied (separation of concerns, sealed Result class)
- Enums include displayName properties for UI display
- System nullable types used appropriately (e.g., optional fields)

## Final Summary

## Implemented Domain Models in core:model Module

### Files Created:
1. **Game.kt** - Core game entity with `Genre` and `Platform` enums
2. **User.kt** - User entity and `UserPreferences` for personalization
3. **Search.kt** - `SearchFilter`, `SortOption` enum, and `SearchResult` for search functionality
4. **Favourite.kt** - `Favourite` and `FavouriteWithGame` for favourites feature
5. **GameDetail.kt** - Extended game details with screenshots, videos, system requirements
6. **Result.kt** - Generic `Result` sealed class for handling success/error/loading states

### Acceptance Criteria Met:
- ✅ **Data classes for all domain entities created** - 6 data classes covering games, users, search, favourites, and results
- ✅ **Models are serializable/parcelable** - All models use `@Serializable` from kotlinx.serialization
- ✅ **Models follow clean architecture principles** - Sealed Result class, proper separation of concerns, immutable data classes
- ✅ **Models are accessible to all modules** - Module is properly configured with serialization plugin and exported to other modules

### Key Design Decisions:
- Used sealed class for Result type to handle success/error/loading states safely
- Added displayName properties to enums for UI display purposes
- Made all data classes immutable (val properties) for thread safety
- Nullable types used for optional fields (e.g., avatarUrl, price, websiteUrl)
