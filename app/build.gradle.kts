plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    val MY_KEY: String by project
    val MY_URL: String by project
    namespace = "com.devhp.newsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.devhp.newsapp"
        minSdk = 23
        //noinspection EditedTargetSdkVersion
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "API_KEY", MY_KEY)
        buildConfigField("String", "BASE_URL", MY_URL)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures { buildConfig = true }

    // Allow references to generated code
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    val retrofit = "2.9.0"
    val coroutines = "1.7.3"
    val gson = "2.10.1"
    val mockWebServer = "4.11.0"
    val truth = "1.1.4"
    val okhttp = "4.11.0"
    val lifecycle = "2.6.2"
    val hilt = "2.44"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.google.code.gson:gson:$gson")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines")
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")
    testImplementation("com.squareup.okhttp3:mockwebserver:$mockWebServer")
    testImplementation("com.google.truth:truth:$truth")
    implementation("com.squareup.okhttp3:okhttp:$okhttp")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle")
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycle")
    implementation("com.google.dagger:hilt-android:$hilt")
    kapt("com.google.dagger:hilt-android-compiler:$hilt")
}