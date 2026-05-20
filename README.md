<img width="1344" height="768" alt="android-arch-sketch" src="https://github.com/user-attachments/assets/ab6502c7-5fdd-46a5-a725-1a183434a4ed" />

# Android Kotlin Base Project

A production-ready Android application template built with modern architecture and best practices.

## 🏗️ Architecture

This project follows **Clean Architecture** with a modular structure:

```
kotlin-android-base/
├── app/                    # Main application module
├── core/                   # Core business logic modules
│   ├── model/             # Domain models & entities
│   ├── data/              # Data layer implementations
│   ├── database/          # Database abstractions
│   └── ui/                # Shared UI components
├── feature/               # Feature modules (clean separation)
│   ├── discover/          # Discover feature
│   ├── search/            # Search feature
│   ├── detail/            # Detail view feature
│   └── favourites/        # Favourites management
└── backlog/               # Project backlog & task tracking
```

## 🚀 Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Kotlin |
| **UI** | Jetpack Compose + Material 3 |
| **DI** | Hilt (Dagger) |
| **Architecture** | Clean Architecture + MVVM |
| **Navigation** | Navigation Compose |
| **Build** | Gradle 8.4 + KSP |
| **Testing** | JUnit 4, MockK, Coroutines Test, Turbine |

## 📦 Build Variants

### Product Flavors
| Flavor | Application ID Suffix | Premium |
|--------|----------------------|---------|
| `free` | `.free` | ❌ |
| `premium` | `.premium` | ✅ |

### Build Types
| Type | Minify | Logging | Purpose |
|------|--------|---------|---------|
| `debug` | ❌ | ✅ | Development |
| `release` | ✅ | ❌ | Production |

## 🛠️ Build Commands

```bash
# Debug build (free flavor)
./gradlew assembleFreeDebug

# Debug build (premium flavor)
./gradlew assemblePremiumDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew test

# Lint check
./gradlew lint
```

## ⚙️ Configuration

### Release Signing
1. Place your keystore file: `app/release-keystore.jks`
2. Configure environment variables or `gradle.properties`:
   ```
   RELEASE_KEYSTORE_PATH=path/to/keystore.jks
   RELEASE_KEYSTORE_PASSWORD=your_password
   RELEASE_KEY_ALIAS=your_alias
   RELEASE_KEY_PASSWORD=your_key_password
   ```

### SDK Requirements
- **Compile SDK**: 34
- **Min SDK**: 26
- **Target SDK**: 33
- **Java**: 17

## 📁 Module Details

### Core Modules
- **`core/model`**: Domain entities (Game, Genre, Platform) and data models
- **`core/data`**: Data sources, repository implementations
- **`core/database`**: Room/database abstractions and DAOs
- **`core/ui`**: Reusable Compose components (BaseViewModel, CommonScreens)

### Feature Modules
Each feature is self-contained with its own:
- **Presentation layer** (UI Composables, ViewModels)
- **Contract** (UiState, Intent, Effect sealed classes)
- **Data** (Repository implementations)

#### Favourites Feature (`feature/favourites`)
The latest feature module providing favourites management functionality:

| Component | Description |
|-----------|-------------|
| `FavouritesViewModel` | Manages UI state and handles user intents |
| `FavouritesContract` | Sealed classes for State, Intent, Effect |
| `FavouritesScreen` | Compose UI for displaying favourites |
| `FavouritesRepository` | Data layer for favourites persistence |

**Intents Supported:**
- `LoadFavourites` - Initial load of favourites
- `RefreshFavourites` - Pull-to-refresh support
- `RemoveFavourite(gameId)` - Remove from favourites
- `GameClicked(gameId)` - Navigate to detail
- `ClearError` - Clear error state

**Effects Emitted:**
- `NavigateToGameDetail(gameId)` - Navigation event
- `ShowMessage(message)` - Toast/Snackbar
- `ShowError(message)` - Error display

## 🧪 Testing

### Unit Tests
```bash
# Run unit tests for all modules
./gradlew testDebugUnitTest

# Run tests for specific feature
./gradlew :feature:favourites:testDebugUnitTest
```

### Testing Stack
- **JUnit 4** - Test framework
- **MockK** - Mocking library for Kotlin
- **Coroutines Test** - Testing coroutines and flows
- **Turbine** - Testing Kotlin Flows and StateFlow

### Example Test (FavouritesViewModel)
```kotlin
@Test
fun `initial state loads favourites`() = runTest {
    every { mockUserRepository.getFavourites() } returns flowOf(testFavourites)
    val viewModel = createViewModel()

    testDispatcher.scheduler.advanceUntilIdle()

    viewModel.uiState.test {
        val loadedState = awaitItem()
        assertFalse(loadedState.isLoading)
        assertEquals(3, loadedState.favourites.size)
    }
}
```

### Test Results (Favourites Feature)
| Status | Tests |
|--------|-------|
| ✅ Passing | 11/11 |

- Initial state loading (empty & non-empty)
- Intent handling (Load, Refresh, Remove, ClearError)
- Effect emission (Navigation, Messages, Errors)
- State management (isEmpty, error state)

## 📄 License

This project is for educational and production use.
