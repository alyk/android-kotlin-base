# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see:
#   http://developer.android.com/guide/developing/tools/proguard.html
#   http://www.guardsquare.com/en/proguard/manual/examples

# =============================================================================
# General Optimization Rules
# =============================================================================

# Enable optimization and obfuscation
-optimizationpasses 5
-dontusemixedcaseclassnames
-verbose

# Keep line numbers for debugging stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Preserve annotations
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# Kotlin specific
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

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

# =============================================================================
# Compose
# =============================================================================

# Compose compiler optimizations
-dontwarn androidx.compose.**

# Keep Compose classes
-keep class androidx.compose.** { *; }

# Preserve Compose runtime
-keep class androidx.compose.runtime.** { *; }

# =============================================================================
# Hilt / Dagger
# =============================================================================

# Hilt specific rules
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ComponentSupplier { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }

# Keep generated Hilt classes
-keepclasseswithmembers class * {
    @dagger.hilt.* <methods>;
}
-keepclasseswithmembers class * {
    @dagger.hilt.* <fields>;
}

# KSP generated classes
-keep class * extends com.google.devtools.ksp.processing.Resolver { *; }
-keep class **Factory { *; }
-keep class **MemberInjector { *; }
-keep class **ModuleAdapter { *; }
-keep class **ProviderAdapter { *; }

# =============================================================================
# Navigation Component
# =============================================================================

-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**

# =============================================================================
# Serialization (Kotlinx Serialization)
# =============================================================================

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class com.example.**$$serializer { *; }
-keepclassmembers class com.example.** {
    *** Companion;
}
-keepclasseswithmembers class com.example.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep data classes for serialization
-keep class com.example.core.model.** { *; }

# =============================================================================
# OkHttp / Retrofit
# =============================================================================

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault

-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# =============================================================================
# Room Database
# =============================================================================

-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# =============================================================================
# Coil (Image Loading)
# =============================================================================

-keep class coil.** { *; }
-dontwarn coil.**

# =============================================================================
# DataStore / Preferences
# =============================================================================

-keep class androidx.datastore.** { *; }
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite {
    <fields>;
}

# =============================================================================
# App-Specific Rules
# =============================================================================

# Keep application class
-keep class com.example.test23.GameApp { *; }

# Keep navigation destinations
-keep class com.example.test23.navigation.Screen { *; }

# Keep feature module classes
-keep class com.example.feature.** { *; }
-keep class com.example.core.** { *; }

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializable implementations
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Remove logging in release builds
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# =============================================================================
# Known Issues / Workarounds
# =============================================================================

# Workaround for KSP generating classes that might cause issues
-keep class **Binder : public extends android.os.Binder { *; }
-keep class **Stub : public extends android.os.Binder { *; }

# Prevent stripping of R8
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Compose compiler may generate classes that need preservation
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**