# =============================================================================
# Core Database Module - ProGuard Rules
# =============================================================================
#
# This file contains ProGuard rules specific to the database module which uses:
# - Room for local database
# - Hilt for dependency injection
#
# =============================================================================

# Keep database module classes
-keep class com.example.core.database.** { *; }

# =============================================================================
# Room Database
# =============================================================================

# Keep Room entities
-keep @androidx.room.Entity class *
-keepclassmembers class * {
    @androidx.room.* <fields>;
    @androidx.room.* <methods>;
}

# Keep Room DAOs
-keep @androidx.room.Dao class *
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# Keep Room database
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Database class *

# Room generated code
-keep class **_*Factory { *; }
-keep class **_*Impl { *; }
-keep class **_*Adapter { *; }
-dontwarn androidx.room.paging.**

# =============================================================================
# Coroutines
# =============================================================================

-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

# =============================================================================
# Hilt
# =============================================================================

-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keepclasseswithmembers class * {
    @dagger.hilt.* <methods>;
}
-keepclasseswithmembers class * {
    @dagger.hilt.* <fields>;
}

# Keep entity classes
-keep class com.example.core.database.entity.** { *; }
-keep class com.example.core.model.** { *; }