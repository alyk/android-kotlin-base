plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

// Signing configuration for release builds
// To enable release signing, create a keystore file and configure the following:
// 1. Place your keystore file in app/release-keystore.jks
// 2. Create gradle.properties with:
//    RELEASE_KEYSTORE_PATH=path/to/release-keystore.jks
//    RELEASE_KEYSTORE_PASSWORD=your_keystore_password
//    RELEASE_KEY_ALIAS=your_key_alias
//    RELEASE_KEY_PASSWORD=your_key_password
// Or configure signingConfig below directly (less secure for shared repositories)
signingConfigs {
    create("release") {
        // Uncomment and configure for production release:
        // storeFile = file(System.getenv("RELEASE_KEYSTORE_PATH") ?: "release-keystore.jks")
        // storePassword = System.getenv("RELEASE_KEYSTORE_PASSWORD") ?: "keystore_password"
        // keyAlias = System.getenv("RELEASE_KEY_ALIAS") ?: "key_alias"
        // keyPassword = System.getenv("RELEASE_KEY_PASSWORD") ?: "key_password"
    }
}

android {
    namespace = "com.example.test23"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.test23"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Enable vector drawable support for older devices
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            buildConfigField("Boolean", "ENABLE_LOGGING", "true")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Enable signing for release builds
            // signingConfig = signingConfigs.getByName("release")

            // Build config fields for release
            buildConfigField("Boolean", "ENABLE_LOGGING", "false")

            // Optimization flags
            crunchPngs = true
        }
    }

    // Product flavors for different build variants (optional)
    flavorDimensions += "version"
    productFlavors {
        create("free") {
            dimension = "version"
            applicationIdSuffix = ".free"
            versionNameSuffix = "-free"
            buildConfigField("Boolean", "IS_PREMIUM", "false")
        }
        create("premium") {
            dimension = "version"
            applicationIdSuffix = ".premium"
            versionNameSuffix = "-premium"
            buildConfigField("Boolean", "IS_PREMIUM", "true")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"

        // Enable compiler optimizations
        freeCompilerArgs += listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-Xjvm-default=all"
        )
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    // Compose compiler reports for optimization analysis (optional)
    // composeCompiler {
    //     reportsDestination = layout.buildDirectory.dir("compose_compiler_reports")
    // }

    // Packaging options to reduce APK size
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/LICENSE.md"
            excludes += "/META-INF/LICENSE-notice.md"
        }
    }

    // Lint configuration for release builds
    lint {
        abortOnError = false
        warningsAsErrors = false
        checkDependencies = true
        xmlReport = true
        htmlReport = true
    }

    // Test configuration
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2024.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Kotlinx
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Feature modules
    implementation(project(":feature:discover"))
    implementation(project(":feature:search"))
    implementation(project(":feature:detail"))
    implementation(project(":feature:favourites"))

    // Core modules
    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:database"))
    implementation(project(":core:ui"))

    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.09.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}