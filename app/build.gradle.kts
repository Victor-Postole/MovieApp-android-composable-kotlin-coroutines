import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.movie_app.api"
    compileSdk = 35



    defaultConfig {
        applicationId = "com.movie_app.api"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val configProperties = Properties()
        val configPropertiesFile = file("${project.rootDir}/config.properties")

        if (configPropertiesFile.exists()) {
            configProperties.load(FileInputStream(configPropertiesFile))
        }

        buildConfigField("String", "API_KEY", "\"${configProperties["API_KEY"]}\"")
        buildConfigField("String", "YOUTUBE_API_KEY", "\"${configProperties["YOUTUBE_API_KEY"]}\"")


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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
    implementation("junit:junit:4.12")
    implementation("androidx.media3:media3-common-ktx:1.5.1")


    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation ("androidx.compose.material:material:1.6.2")

    // Extended Icons
    implementation("androidx.compose.material:material-icons-extended:1.6.2")

    // Room
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-paging:2.6.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    implementation ("ch.qos.logback:logback-classic:1.4.11")

    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-compiler:2.44")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // System ui controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Youtube Player
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.0.0")

// Retrofit & MockWebServer
    testImplementation("com.squareup.retrofit2:retrofit:2.9.0")
    testImplementation("com.squareup.retrofit2:converter-gson:2.9.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.0")
    androidTestImplementation ("com.squareup.okhttp3:mockwebserver3:5.0.0-alpha.2")

    // Unit Test Libraries
    testImplementation("io.mockk:mockk:1.13.5")  // Keep only one version of MockK
    testImplementation("junit:junit:4.13.2")     // Keep JUnit 4 for unit tests
    testImplementation("org.jetbrains.kotlin:kotlin-test") // Kotlin test support
    testImplementation("org.mockito:mockito-core:4.2.0")  // Mocking with Mockito
    testImplementation("androidx.room:room-testing:2.5.0") // Room testing support
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3") // Coroutine testing support

    // Android Test Libraries (if needed for instrumentation tests)
    androidTestImplementation("io.mockk:mockk-android:1.13.5") // MockK for Android tests
    androidTestImplementation("androidx.test.ext:junit:1.1.5")   // JUnit for Android testing
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Espresso for UI testing
    androidTestImplementation("androidx.compose.ui:ui-test-junit4") // Compose UI testing
    androidTestImplementation("org.junit.jupiter:junit-jupiter:5.8.1") // JUnit 5 for Android tests


    testImplementation ("org.mockito:mockito-core:4.3.1")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")


    // Debug and Testing Tools
    debugImplementation("androidx.compose.ui:ui-tooling")   // Debug tools for Compose UI
    debugImplementation("androidx.compose.ui:ui-test-manifest") // For testing manifests in Compose
}












