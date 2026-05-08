---
id: task-12
title: Write FavouritesScreen instrumented tests
status: In Progress
priority: medium
milestone: Favourites Feature
assignee: []
created_date: '2026-05-08 18:56'
updated_date: '2026-05-08 21:14'
labels:
  - testing
  - instrumented-tests
  - ui
dependencies: []
references: []
documentation: []
ordinal: 1000
---

Write instrumented tests for FavouritesScreen UI. Test grid display, empty state, navigation to detail screen, and remove functionality.

## Implementation Notes

Created FavouritesScreen instrumented tests in feature/favourites/src/androidTest/kotlin/com/example/feature/favourites/FavouritesScreenTest.kt

Added comprehensive test coverage including: grid display, empty state, loading state, error state, navigation to detail screen, remove functionality via swipe and button click, back navigation, and retry functionality

Added required androidTest dependencies to feature/favourites/build.gradle.kts including: androidx.test.ext:junit, androidx.test.espresso:espresso-core, androidx.compose.ui:ui-test-junit4, and mockk-android dependencies

Created test directory structure: feature/favourites/src/androidTest/kotlin/com/example/feature/favourites/

