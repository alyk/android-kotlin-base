---
id: task-1
title: "Create FavouriteGameEntity in core:database"
status: Done
priority: high
milestone: Favourites Feature
assignee: []
created_date: '2026-05-08 18:56'
updated_date: '2026-05-08 18:58'
labels:
  - database
  - entity
  - room
dependencies: []
references: []
documentation: []
ordinal: 1000
---

Add FavouriteGameEntity class with fields: id, gameId, title, thumbnailUrl, genre, platform, rating, savedAt. Annotate with Room @Entity and define primary key.

## Implementation Notes

Created FavouriteGameEntity class in core/database/src/main/kotlin/com/example/core/database/entity/GameEntity.kt

Added fields: id (auto-generated primary key), gameId, title, thumbnailUrl, genre, platform, rating, savedAt

Annotated with @Entity(tableName = \"favourite_games\")

Used autoGenerate = true for primary key to allow multiple favourites

## Final Summary

Successfully implemented FavouriteGameEntity in the core:database module. Created a new Room entity class with all required fields: id (auto-generated primary key), gameId, title, thumbnailUrl, genre, platform, rating, and savedAt. The entity is annotated with @Entity(tableName = \"favourite_games\") and uses autoGenerate = true for the primary key to support multiple favourite entries. This provides the foundation for a device-local favourites system that doesn't require user authentication.
