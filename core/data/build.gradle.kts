plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.example.core.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
}