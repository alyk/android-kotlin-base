plugins {
    id("com.android.library")

}

android {
    namespace = "com.example.feature.favourites"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
}

dependencies {
    implementation(project(":core:ui"))
}
