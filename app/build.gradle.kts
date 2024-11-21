plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id ("dagger.hilt.android.plugin")
}
android {
    namespace = "com.zaryabshakir.mediagallery"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.zaryabshakir.mediagallery"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    implementation(libs.hilt)
    implementation(libs.junit.ktx)
    kapt(libs.hilt.compiler)

    implementation(libs.glide)
    kapt (libs.glide.compiler)
    implementation (libs.lottie)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation (libs.mockito.core)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.powermock.module.junit4)
    testImplementation (libs.powermock.api.mockito2)
}
tasks.withType<Test> {
    jvmArgs(
        "--add-opens", "java.base/java.lang=ALL-UNNAMED",
        "--add-opens", "java.base/java.util=ALL-UNNAMED"
    )
}


