plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    compileSdk 34

    defaultConfig {
        applicationId "com.example.temperatureapp"
        minSdk 24
        targetSdk 34
        versionCode 1
        versionName "1.0"
    }

    buildFeatures {
        compose true
    }

    composeOptions {
        kotlinCompilerExtensionVersion '1.5.10'
    }

    kotlinOptions {
        jvmTarget = '1.8'
    }
}

dependencies {
    implementation "androidx.core:core-ktx:1.12.0"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.6.2"
    implementation "androidx.activity:activity-compose:1.8.2"
    implementation "androidx.compose.ui:ui:1.5.4"
    implementation "androidx.compose.material:material:1.5.4"
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2"

    // Retrofit и Gson
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
}
