---
id: task-8
title: Add favourite toggle to DetailScreen
status: Done
priority: high
milestone: Favourites Feature
assignee: []
created_date: '2026-05-08 18:56'
updated_date: '2026-05-08 19:17'
labels:
  - ui
  - detail-screen
  - interaction
dependencies: []
references: []
documentation: []
ordinal: 1000
---

Add heart icon to DetailScreen top bar. Implement toggle functionality that calls repository to add/remove from favourites. Show filled/outlined state based on current status.

## Implementation Notes

Analysis: Favourite toggle functionality is already fully implemented in DetailScreen

Files examined:

- feature/detail/src/main/kotlin/com/example/feature/detail/DetailScreen.kt

- feature/detail/src/main/kotlin/com/example/feature/detail/DetailViewModel.kt

- feature/detail/src/main/kotlin/com/example/feature/detail/DetailContract.kt

- feature/detail/src/test/kotlin/com/example/feature/detail/DetailViewModelTest.kt

Implementation details:

- Heart icon already present in top bar actions

- Toggle functionality calls userRepository.addFavourite/removeFavourite

- Shows filled (red) vs outlined state based on isFavourite status

- Displays snackbar messages for add/remove actions

- Comprehensive unit tests cover all functionality

