plugins {
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("io.realm.kotlin")
}

android {
    namespace = "com.whiskersapps.clawlauncher"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.whiskersapps.clawlauncher"
        minSdk = 29
        targetSdk = 35
        versionCode = 2
        versionName = "0.2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.adaptive.android)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Data Store
    implementation(libs.androidx.datastore.preferences)

    // Whiskers Palette
    implementation(libs.whiskers.palette.kt)

    // Compose Hilt
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // Dagger Hilt
    implementation(libs.hilt.android.v248)
    kapt(libs.hilt.android.compiler.v248)
    kapt(libs.androidx.hilt.compiler)

    // Mongo Realm
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.library.base)

    // Routes
    implementation(libs.androidx.navigation.compose)

    // Coil for async image loading
    implementation(libs.coil.compose)

    // Sniffer (Fuzzy String Matching)
    implementation (libs.sniffer.kt)

    // Fingerprint
    implementation(libs.androidx.biometric)
}

kapt {
    correctErrorTypes = true
}