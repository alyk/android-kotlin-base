---
id: task-5
title: "Implement data services and network layer in core:data module"
status: Done
priority: medium
milestone: Core Module Ready
assignee: []
created_date: '2026-05-07 14:38'
updated_date: '2026-05-07 17:05'
labels:
  - network
  - api
dependencies: []
references: []
documentation: []
ordinal: 1000
---

Implement network layer and data services in core:data module

## Acceptance Criteria

- [x] Network service interfaces defined
- [x] Retrofit/HTTP client configured
- [x] API response handling implemented
- [x] Data transformation utilities available

## Implementation Notes

Implemented data services and network layer in core:data module:

**Files created:**

1. **NetworkClient.kt** - Network service interfaces and configuration:
   - GameApiService interface with all API endpoints (getGames, searchGames, getGameById, etc.)
   - ApiResponse wrapper class for standardized API responses
   - NetworkClient object with configured OkHttpClient, HttpLoggingInterceptor, and Retrofit instance
   - Support for custom base URLs for testing/environments

2. **GameRemoteDataSource.kt** - Remote data source implementation:
   - GameRemoteDataSource class for all network operations
   - Suspended functions for games, featured games, game details, search, and filtering
   - Proper Result wrapper usage for success/error handling
   - Dispatchers.IO for background thread execution

3. **GameRepository.kt** - Repository pattern implementation:
   - GameRepository interface defining data operations
   - GameRepositoryImpl with remote data source integration
   - Clean separation between interface and implementation

4. **GameMapper.kt** - Data transformation utilities:
   - Extension functions for genre/platform conversions
   - GameDto and GameDetailDto data classes for API transformation
   - SystemRequirementsDto for PC requirements
   - Helper functions for rating formatting and date conversion
   - Domain model conversion functions

5. **NetworkExceptions.kt** - Custom exception handling:
   - NoNetworkException, NetworkTimeoutException, ServerException
   - NotFoundException, UnauthorizedException, ConflictException
   - RateLimitException with retry information
   - Extension function for mapping standard exceptions

## Final Summary

## Implemented Data Services and Network Layer in core:data Module

### Files Created in `core/data/src/main/kotlin/com/example/core/data/`:

**Network Layer (`network/`):**
- **NetworkClient.kt** - Retrofit service interface, API response wrapper, and configured OkHttp/Retrofit clients with logging and timeout configuration

**Data Source (`datasource/`):**
- **GameRemoteDataSource.kt** - Remote data source class with suspended functions for all game-related API calls

**Repository Layer (`repository/`):**
- **GameRepository.kt** - Repository interface and implementation using the remote data source

**Data Transformation (`mapper/`):**
- **GameMapper.kt** - DTOs and mapping functions for converting between API responses and domain models

**Exception Handling (`exception/`):**
- **NetworkExceptions.kt** - Custom exception classes for different network error scenarios

### Acceptance Criteria Met:
- ✅ **Network service interfaces defined** - GameApiService with comprehensive API endpoints
- ✅ **Retrofit/HTTP client configured** - NetworkClient with OkHttpClient, logging, timeouts, and custom base URL support
- ✅ **API response handling implemented** - ApiResponse wrapper, Result sealed class usage, custom exceptions
- ✅ **Data transformation utilities available** - GameMapper with DTOs, domain conversions, and helper functions

### Key Design Decisions:
- Used Retrofit with kotlinx.serialization converter for type-safe JSON parsing
- Repository pattern abstracts data source from the rest of the application
- Custom exceptions provide detailed error information for proper error handling
- Mapper functions handle data transformation between API DTOs and domain models
- All network operations run on Dispatchers.IO for proper coroutine handling
