# Android Kotlin Base Project

A production-ready Android application template built with modern architecture and best practices.

## 🏗️ Architecture

This project follows **Clean Architecture** with a modular structure:

```
test23/
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
| **Testing** | JUnit 4, MockK, Coroutines Test |

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
- **Java**: 11

## 📁 Module Details

### Core Modules
- **`core/model`**: Domain entities and data models
- **`core/data`**: Data sources and repositories
- **`core/database`**: Room/database implementations
- **`core/ui`**: Reusable Compose components

### Feature Modules
Each feature is self-contained with its own:
- Presentation layer (UI, ViewModels)
- Domain use cases
- Data implementations

## 🧪 Testing

```bash
# Unit tests
./gradlew testDebugUnitTest

# Instrumented tests
./gradlew connectedAndroidTest
```

## 📄 License

This project is for educational and production use.