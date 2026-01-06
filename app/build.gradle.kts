plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
//    id("com.google.dagger.hilt.android")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")

    id("kotlin-parcelize")

}

android {
    namespace = "com.vgroup.vgroupstore"
    compileSdk = 34

//    kotlinOptions {
//        jvmTarget = "11"
//        freeCompilerArgs += ["-Xjvm-default=all"]
//    }

    defaultConfig {
        applicationId = "com.vgroup.vgroupstore"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("com.google.code.gson:gson:2.10.1")
//    // Hilt
//    implementation("com.google.dagger:hilt-android:2.51.1")
//    kapt("com.google.dagger:hilt-compiler:2.51.1")
//
//// Hilt ViewModel
//    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
//    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Hilt Core
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    // Hilt for Navigation/ViewModels
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")
    kapt("androidx.hilt:hilt-compiler:1.2.0")



// Required for viewModels()
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")

// Jetpack Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.2")

    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")

    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.7.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.11.3")

    // Room runtime
    implementation("androidx.room:room-runtime:2.6.1")

    // Kotlin extensions and Coroutines support
    implementation("androidx.room:room-ktx:2.6.1")

    // Annotation processor
    kapt("androidx.room:room-compiler:2.6.1")

}