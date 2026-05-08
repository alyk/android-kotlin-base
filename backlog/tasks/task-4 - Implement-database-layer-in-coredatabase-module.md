---
id: task-4
title: "Implement database layer in core:database module"
status: Done
priority: medium
milestone: Core Module Ready
assignee: []
created_date: '2026-05-07 14:38'
updated_date: '2026-05-07 17:08'
labels:
  - database
  - room
dependencies: []
references: []
documentation: []
ordinal: 1000
---

Set up database layer with Room and data access patterns in core:database module

## Acceptance Criteria

- [x] Database entities and DAOs implemented
- [x] Repository interfaces defined
- [x] Data sources configured
- [x] Error handling implemented

## Implementation Notes

Implemented database layer in core:database module:

**Files created:**

1. **GameEntity.kt** - Room entity for games:
   - GameEntity with all game fields and cached timestamp
   - GameDetailEntity for extended game information (screenshots, videos, system requirements)
   - SearchCacheEntity for caching search results with expiration
   - Conversion functions to/from domain models

2. **UserEntities.kt** - Room entities for users and favourites:
   - UserEntity with user data and preferences (JSON serialized lists)
   - FavouriteEntity with foreign keys to users and games
   - FavouriteWithGameEntity for joined queries with game details
   - Serialization/deserialization for preferences

3. **GameDao.kt** - Data Access Object for games:
   - CRUD operations: insert, update, delete, get by ID
   - Filter operations: by genre, platform, rating
   - Search operations: by title with Flow support
   - Game detail operations and search cache management
   - Reactive Flow methods for observing data changes

4. **UserDao.kt** - Data Access Object for users and favourites:
   - User CRUD operations with Flow support
   - Favourite operations: add, remove, list
   - FavouriteWithGame query joining favourites and games
   - isFavourited check and count queries with Flow support

5. **AppDatabase.kt** - Room database configuration:
   - AppDatabase class extending RoomDatabase
   - DAOs for games and users
   - Singleton pattern with double-checked locking
   - In-memory database support for testing

6. **GameLocalRepository.kt** - Game repository implementation:
   - GameLocalRepository interface defining operations
   - GameLocalRepositoryImpl with Result wrapper usage
   - Flow-based reactive methods for observing data
   - Game detail save/retrieve with system requirements

7. **UserLocalRepository.kt** - User repository implementation:
   - UserLocalRepository interface for user operations
   - UserLocalRepositoryImpl with preferences update support
   - Favourite management (add, remove, check status)
   - Reactive Flow methods for observing user and favourite data

8. **DatabaseDataSource.kt** - Data source factory:
   - DatabaseDataSource singleton for database access
   - Factory methods for DAOs and repositories
   - DatabaseModule for dependency injection
   - Instance management and cleanup

9. **DatabaseExceptions.kt** - Custom exception handling:
   - DatabaseException, NotFoundException, ConstraintViolationException
   - ConcurrencyException, MigrationException, InvalidDatabaseStateException
   - InsufficientStorageException, TransactionException
   - Extension function for mapping standard exceptions

## Final Summary

## Implemented Database Layer in core:database Module

### Files Created in `core/database/src/main/kotlin/com/example/core/database/`:

**Entities (`entity/`):**
- **GameEntity.kt** - Room entities for games, game details, and search cache with domain conversion functions
- **UserEntities.kt** - Room entities for users, favourites, and favourite-with-game joins with preferences serialization

**Data Access Objects (`dao/`):**
- **GameDao.kt** - Complete CRUD operations, filtering, search, cache management with Flow support
- **UserDao.kt** - User operations, favourite management with foreign keys, and reactive Flow methods

**Database Configuration:**
- **AppDatabase.kt** - Room database with singleton pattern, DAOs, and testing support

**Repository Layer (`repository/`):**
- **GameLocalRepository.kt** - Repository interface and implementation with Result wrapper and Flow support
- **UserLocalRepository.kt** - User repository with preferences management and favourite operations

**Data Source Factory (`datasource/`):**
- **DatabaseDataSource.kt** - Singleton factory for DAOs and repositories with DI module

**Exception Handling (`exception/`):**
- **DatabaseExceptions.kt** - Custom exceptions for database operations with mapping functions

### Acceptance Criteria Met:
- ✅ **Database entities and DAOs implemented** - 4 entities and 2 DAOs with comprehensive operations
- ✅ **Repository interfaces defined** - GameLocalRepository and UserLocalRepository with full CRUD
- ✅ **Data sources configured** - DatabaseDataSource factory with singleton pattern and DI module
- ✅ **Error handling implemented** - Custom exceptions with proper mapping from standard database exceptions

### Key Design Decisions:
- Used Room with kapt compiler for annotation processing
- Foreign keys with cascade delete for data integrity
- Indexed columns for performance optimization on frequent queries
- Flow-based reactive methods for observing data changes in real-time
- JSON serialized lists for complex data (genres, platforms, tags)
- Search cache with 24-hour expiration for offline search functionality
- Repository pattern abstracts database implementation from feature modules
- Result wrapper for consistent success/error handling across all operations
