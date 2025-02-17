import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.piedpiper"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.piedpiper"
        minSdk = 24
        targetSdk = 34
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

    buildFeatures{

        viewBinding = true
    }


    dependencies {
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("com.squareup.okhttp3:okhttp:4.9.2")
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
    }
}
dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
}

