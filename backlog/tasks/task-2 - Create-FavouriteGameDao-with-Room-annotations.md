---
id: task-2
title: Create FavouriteGameDao with Room annotations
status: Done
priority: high
milestone: Favourites Feature
assignee: []
created_date: '2026-05-08 18:56'
updated_date: '2026-05-08 18:59'
labels:
  - database
  - dao
  - room
dependencies: []
references: []
documentation: []
ordinal: 1000
---

Create FavouriteGameDao interface with methods: insert(favourite), delete(favourite), getAllFavourites(): Flow<List<FavouriteGameEntity>>, isFavourite(gameId): Flow<Boolean>. Add to AppDatabase.

## Implementation Notes

Created FavouriteGameDao interface in core/database/src/main/kotlin/com/example/core/database/dao/FavouriteGameDao.kt

Added methods: insert(favourite), delete(favourite), getAllFavourites(): Flow<List<FavouriteGameEntity>>, isFavourite(gameId): Flow<Boolean>

Added additional utility methods: getFavouriteByGameId(), deleteByGameId(), getFavouriteCount(), deleteAllFavourites()

Updated AppDatabase.kt to include FavouriteGameEntity in entities list and add favouriteGameDao() abstract method

Added proper Room annotations: @Insert, @Delete, @Query with appropriate SQL statements

## Final Summary

Successfully implemented FavouriteGameDao with all required Room annotations. Created a new DAO interface with methods: insert(favourite), delete(favourite), getAllFavourites(): Flow<List<FavouriteGameEntity>>, and isFavourite(gameId): Flow<Boolean>. Added the DAO to AppDatabase by including FavouriteGameEntity in the entities list and adding the favouriteGameDao() abstract method. The implementation follows the existing code patterns and includes additional utility methods for comprehensive functionality.
