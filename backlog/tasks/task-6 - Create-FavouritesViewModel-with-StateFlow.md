---
id: task-6
title: Create FavouritesViewModel with StateFlow
status: Done
priority: high
milestone: Favourites Feature
assignee: []
created_date: '2026-05-08 18:56'
updated_date: '2026-05-08 20:58'
labels:
  - viewmodel
  - state-management
  - coroutines
dependencies: []
references: []
documentation: []
ordinal: 1000
---

Create FavouritesViewModel with StateFlow<FavouritesUiState>. Implement sealed interface: Loading, Success(games), Error(message). Handle loading, error states.

## Acceptance Criteria

- [ ] Create FavouritesViewModel class with @HiltViewModel annotation
- [ ] Define sealed interface FavouritesUiState with Loading, Success, and Error states
- [ ] Implement StateFlow<FavouritesUiState> for UI state management
- [ ] Inject FavouritesRepository dependency
- [ ] Handle loading states during operations
- [ ] Handle error states with proper error messages
- [ ] Implement core operations: loadFavourites, addFavourite, removeFavourite
- [ ] Create FavouritesViewModel class with @HiltViewModel annotation
- [ ] Define sealed interface FavouritesUiState with Loading, Success, and Error states
- [ ] Implement StateFlow<FavouritesUiState> for UI state management
- [ ] Inject FavouritesRepository dependency
- [ ] Handle loading states during operations
- [ ] Handle error states with proper error messages
- [ ] Implement core operations: loadFavourites, addFavourite, removeFavourite

## Implementation Notes

Starting task-6: Create FavouritesViewModel with StateFlow. Reviewing existing codebase structure.

## Final Summary

FavouritesViewModel with StateFlow has been successfully implemented with:
- @HiltViewModel annotated ViewModel class
- Sealed interface FavouritesUiState with Loading, Success, and Error states
- StateFlow<FavouritesUiState> for reactive UI state management
- FavouritesRepository dependency injection
- Loading state handling during operations
- Error state handling with proper error messages
- Core operations: loadFavourites, addFavourite, removeFavourite, refreshFavourites, clearError
- Side effects via SharedFlow (FavouritesEffect) for navigation and snackbars
