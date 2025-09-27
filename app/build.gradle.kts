import org.jetbrains.kotlin.gradle.dsl.JvmTarget
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.paris_2.san3a"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.paris_2.san3a"
        minSdk = 26
        targetSdk = 36
        versionCode = project.findProperty("versionCode")?.toString()?.toInt() ?: 1
        versionName = project.findProperty("versionName")?.toString() ?: "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "WHATSAPP_API_KEY", "\"${project.findProperty("WHATSAPP_API_KEY")}\"")
        buildConfigField("String", "SESSION_ID", "\"${project.findProperty("SESSION_ID")}\"")

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
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Koin
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.android)

    //Nav
    implementation(libs.navigation.compose)

    //kotlinx serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.firebase.bom))


    //firestore
    implementation(libs.firebase.firestore.ktx)

    //firebase storge
    implementation(libs.firebase.storage)

    // splash api
    implementation(libs.androidx.core.splashscreen)

    //Kotlinx DateTime
    implementation(libs.kotlinx.datetime)

    //coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //dataStore
    implementation(libs.androidx.datastore.preferences)

    implementation(platform(libs.firebase.bom))

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.accompanist.flowlayout)
    implementation(libs.logging.interceptor)

    // Locale
    implementation("com.vanniktech:multiplatform-locale:0.9.0")

}