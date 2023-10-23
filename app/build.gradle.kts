@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.jalalkun.movieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jalalkun.movieapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "MOVIE_GENRE", "\"genre/movie/list?language=en\"")
            buildConfigField("String", "MOVIE_LIST", "\"discover/movie\"")
            buildConfigField("String", "IMAGE_URL", "\"https://image.tmdb.org/t/p/original\"")
            buildConfigField("String", "MOVIE_VIDEO", "\"movie/{movie_id}/videos\"")
            buildConfigField("String", "MOVIE_DETAIL", "\"movie/{movie_id}\"")
            buildConfigField("String", "MOVIE_REVIEW", "\"movie/{movie_id}/reviews\"")
        }

        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "MOVIE_GENRE", "\"genre/movie/list?language=en\"")
            buildConfigField("String", "MOVIE_LIST", "\"discover/movie\"")
            buildConfigField("String", "IMAGE_URL", "\"https://image.tmdb.org/t/p/original\"")
            buildConfigField("String", "MOVIE_VIDEO", "\"movie/{movie_id}/videos\"")
            buildConfigField("String", "MOVIE_DETAIL", "\"movie/{movie_id}\"")
            buildConfigField("String", "MOVIE_REVIEW", "\"movie/{movie_id}/reviews\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.koin.core)
    implementation(libs.koin.androidx.compose)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    implementation(libs.okhttp)

    debugImplementation(libs.pluto)
    releaseImplementation(libs.pluto.no.op)
    debugImplementation(libs.pluto.logger)
    releaseImplementation(libs.pluto.logger.no.op)
    debugImplementation(libs.pluto.network)
    releaseImplementation(libs.pluto.network.no.op)
    debugImplementation(libs.pluto.network.interceptor.okhttp)
    releaseImplementation(libs.pluto.network.interceptor.okhttp.no.op)
    implementation(libs.coil.compose)


    implementation(libs.androidx.material.icons.extended.android)
    implementation(libs.youtubeplayer.core)

    //room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)

    // moshi
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

}