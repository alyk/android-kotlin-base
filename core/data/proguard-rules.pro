# =============================================================================
# Core Data Module - ProGuard Rules
# =============================================================================
#
# This file contains ProGuard rules specific to the data module which uses:
# - Retrofit for networking
# - Kotlinx Serialization for JSON parsing
# - OkHttp for HTTP client
#
# =============================================================================

# Keep data module classes
-keep class com.example.core.data.** { *; }

# =============================================================================
# Retrofit
# =============================================================================

-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# Keep generated Retrofit adapters
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# =============================================================================
# OkHttp
# =============================================================================

-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep class okio.** { *; }
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# =============================================================================
# Kotlinx Serialization
# =============================================================================

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep serialization serializers
-keep,includedescriptorclasses class com.example.core.data.**$$serializer { *; }
-keepclassmembers class com.example.core.data.** {
    *** Companion;
}
-keepclasseswithmembers class com.example.core.data.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep network response classes
-keep class com.example.core.data.remote.** { *; }
-keep class com.example.core.data.dto.** { *; }

# Keep model classes
-keep class com.example.core.model.** { *; }

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