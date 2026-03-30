// ✅ Plugins for Android, Firebase, and Kotlin
plugins {
    id("com.android.application")
    id("com.google.gms.google-services")  // Required for Firebase
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.ar_application"
    compileSdk = 35  // Ensure the correct SDK

    defaultConfig {
        applicationId = "com.example.ar_application"
        minSdk = 28
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // Set to 17
        targetCompatibility = JavaVersion.VERSION_17 // Set to 17
    }

    kotlinOptions {
        jvmTarget = "17" // Ensure Kotlin uses the same JVM version
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies {
    // ✅ Core Android Libraries
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    

    // ✅ Firebase Dependencies (Consistent Versions)
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-firestore:24.9.1")
    implementation("com.google.firebase:firebase-storage:20.2.0")

    // ✅ Glide for Image Loading
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation(libs.runtime)
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    // ✅ SceneView (Downgraded for VideoNode support)
    //implementation("io.github.sceneview:arsceneview:0.9.0")
    implementation("io.github.sceneview:arsceneview:0.10.0")// Ensure compatibility

    // ✅ Testing Dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // ✅ Core AndroidX Dependencies
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

}
