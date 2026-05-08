---
id: task-10
title: Write FavouritesViewModel unit tests
status: Done
priority: medium
milestone: Favourites Feature
assignee: []
created_date: '2026-05-08 18:56'
updated_date: '2026-05-08 21:06'
labels:
  - testing
  - unit-tests
  - viewmodel
dependencies: []
references: []
documentation: []
ordinal: 1000
---

Write unit tests for FavouritesViewModel covering all states: loading, success, error, toggle functionality. Mock repository responses.

## Implementation Notes

Verified existing test file: FavouritesViewModelTest.kt

Tests already comprehensive covering all acceptance criteria:

- Loading state tests

- Success state tests

- Error state tests

- Toggle functionality (add/remove favourite)

- Repository mock responses used with MockK

- Effects flow testing with Turbine

Tests executed successfully: :feature:favourites:testDebugUnitTest

## Final Summary

**FavouritesViewModel unit tests are already fully implemented and passing.**

**File:** `feature/favourites/src/test/kotlin/com/example/feature/favourites/FavouritesViewModelTest.kt`

**Existing Tests (11 tests total):**

| Test | Description |
|------|-------------|
| `initialStateShouldBeLoadingWithEmptyList` | Verifies initial Loading state with empty list |
| `loadFavouritesShouldUpdateStateWithSuccessResult` | Tests Success state with data from repository |
| `loadFavouritesShouldUpdateStateWithErrorResult` | Tests Error state when repository fails |
| `refreshFavouritesShouldReloadFavourites` | Tests Refresh intent reloads data |
| `removeFavouriteShouldRemoveGameAndEmitSuccessEffect` | Tests remove functionality + ShowFavouriteRemoved effect |
| `removeFavouriteShouldEmitErrorEffectWhenRemovalFails` | Tests error effect when removal fails |
| `gameClickedShouldEmitNavigateEffect` | Tests NavigateToGameDetail effect |
| `clearErrorShouldRemoveErrorFromState` | Tests ClearError intent |
| `observeFavouritesShouldUpdateStateWhenRepositoryEmitsNewData` | Tests Flow observation for reactive updates |
| `emptyFavouritesListShouldSetIsEmptyToTrue` | Tests isEmpty computed property |
| `addFavouriteShouldAddGameAndEmitSuccessEffect` | Tests add functionality + ShowFavouriteAdded effect |

**Test Implementation Details:**
- Uses MockK for repository mocking
- Uses Turbine for effects flow testing
- Uses UnconfinedTestDispatcher for coroutine testing
- Tests all UI states (Loading, Success, Error)
- Tests all intents (Load, Refresh, Add, Remove, GameClicked, ClearError)
- Tests all effects (Navigate, ShowError, ShowFavouriteAdded/Removed)

**Build Status:** ✅ BUILD SUCCESSFUL - All 11 unit tests pass
