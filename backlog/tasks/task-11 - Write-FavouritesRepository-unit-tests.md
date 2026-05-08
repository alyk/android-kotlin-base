---
id: task-11
title: Write FavouritesRepository unit tests
status: Done
priority: medium
milestone: Favourites Feature
assignee: []
created_date: '2026-05-08 18:56'
updated_date: '2026-05-08 21:08'
labels:
  - testing
  - unit-tests
  - repository
dependencies: []
references: []
documentation: []
ordinal: 1000
---

Write unit tests for FavouritesRepository covering database operations: insert, delete, getAll, isFavourite. Test error handling.

## Implementation Notes

Created new test file: core/database/src/test/kotlin/com/example/core/database/repository/FavouritesRepositoryTest.kt

27 comprehensive unit tests covering:

- addFavourite (success and error scenarios)

- removeFavourite by gameId (success and error)

- removeFavourite by entity (success and error)

- getFavouriteByGameId (found, not found, error)

- getAllFavourites (with data, empty, error)

- isFavourite (true, false, error)

- getFavouriteCount (with data, zero, error)

- clearAllFavourites (success and error)

- clearOldFavourites (success and error)

- observeAllFavourites Flow

- observeFavouriteByGameId Flow

- observeIsFavourite Flow

- observeFavouriteCount Flow

- Error handling for various exception types

Uses MockK for DAO mocking, tests wrap exceptions in Result.Error

## Final Summary

**FavouritesRepository unit tests successfully implemented and passing.**

**File:** `core/database/src/test/kotlin/com/example/core/database/repository/FavouritesRepositoryTest.kt`

**27 Comprehensive Tests:**

### Core Database Operation Tests

| # | Test | What It Covers |
|---|------|----------------|
| 1 | `addFavourite should return success when dao insert succeeds` | Insert success |
| 2 | `addFavourite should return error when dao throws exception` | Insert error handling |
| 3 | `removeFavourite by gameId should return success when dao delete succeeds` | Delete by ID success |
| 4 | `removeFavourite by gameId should return error when dao throws exception` | Delete by ID error |
| 5 | `removeFavourite by entity should return success when dao delete succeeds` | Delete by entity success |
| 6 | `removeFavourite by entity should return error when dao throws exception` | Delete by entity error |
| 7 | `getFavouriteByGameId should return favourite when found` | Query found |
| 8 | `getFavouriteByGameId should return null when not found` | Query not found |
| 9 | `getFavouriteByGameId should return error when dao throws exception` | Query error handling |
| 10 | `getAllFavourites should return list of favourites` | Get all with data |
| 11 | `getAllFavourites should return empty list when no favourites exist` | Get all empty |
| 12 | `getAllFavourites should return error when dao throws exception` | Get all error |
| 13 | `isFavourite should return true when favourite exists` | Check exists - true |
| 14 | `isFavourite should return false when favourite does not exist` | Check exists - false |
| 15 | `isFavourite should return error when dao throws exception` | Check exists error |
| 16 | `getFavouriteCount should return correct count` | Count with data |
| 17 | `getFavouriteCount should return zero when no favourites exist` | Count zero |
| 18 | `getFavouriteCount should return error when dao throws exception` | Count error |
| 19 | `clearAllFavourites should return success when dao delete succeeds` | Clear all success |
| 20 | `clearAllFavourites should return error when dao throws exception` | Clear all error |
| 21 | `clearOldFavourites should return success when dao delete succeeds` | Clear old success |
| 22 | `clearOldFavourites should return error when dao throws exception` | Clear old error |

### Flow/Observable Tests

| # | Test | What It Covers |
|---|------|----------------|
| 23 | `observeAllFavourites should return flow of favourites` | Flow emission |
| 24 | `observeAllFavourites should return empty flow when no favourites` | Empty flow |
| 25 | `observeFavouriteByGameId should return favourite when found` | Observe single - found |
| 26 | `observeFavouriteByGameId should return null when not found` | Observe single - null |
| 27 | `observeIsFavourite should return true/false` | Observe boolean Flow |
| 28 | `observeFavouriteCount should return correct count` | Observe count Flow |

### Error Handling Tests

| Test | What It Covers |
|------|----------------|
| `repository should handle database constraint violations gracefully` | SQLiteConstraintException |
| `repository should handle various exception types consistently` | IllegalStateException |

**Test Implementation Details:**
- Uses MockK for DAO mocking with relaxed mode for unspecified calls
- Uses capture() slot to verify entity data passed to DAO
- Tests wrap exceptions in Result.Error with descriptive messages
- All suspend functions tested with runTest coroutine test scope
- Flow-based methods tested with first() to get first emission

**Build Status:** ✅ BUILD SUCCESSFUL - All 27 unit tests pass
