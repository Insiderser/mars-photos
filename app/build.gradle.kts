import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.0")

    defaultConfig {
        applicationId = "com.insiderser.mars"
        minSdkVersion(16)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "NASA_API_KEY", '"' + getNasaApiKey() + '"')

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.incremental", "true")
            }
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        setSourceCompatibility(JavaVersion.VERSION_1_8)
        setTargetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4-M2")
    implementation("androidx.core:core-ktx:1.3.0")
    implementation("androidx.appcompat:appcompat:1.2.0-rc01")
    implementation("androidx.fragment:fragment-ktx:1.2.5")
    implementation("com.google.android.material:material:1.2.0-beta01")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta7")
    implementation("androidx.recyclerview:recyclerview:1.1.0")

    val lifecycleVersion = "2.2.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycleVersion")

    val pagingVersion = "2.1.2"
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
    testImplementation("androidx.paging:paging-common-ktx:$pagingVersion")
    implementation("androidx.paging:paging-rxjava2-ktx:$pagingVersion")

    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0-rc01")

    val roomVersion = "2.2.5"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-rxjava2:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    implementation("io.reactivex.rxjava2:rxjava:2.2.19")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")

    val daggerVersion = "2.28-alpha"
    val androidxHiltVersion = "1.0.0-alpha01"
    implementation("com.google.dagger:hilt-android:$daggerVersion")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:$androidxHiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$daggerVersion")
    kapt("androidx.hilt:hilt-compiler:$androidxHiltVersion")

    implementation("com.squareup.picasso:picasso:2.71828")

    implementation("com.jakewharton.timber:timber:4.7.1")

    implementation("dev.chrisbanes:insetter-ktx:0.3.0")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.4")

    testImplementation("junit:junit:4.13")
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    androidTestImplementation("androidx.test:core:1.2.0")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test:rules:1.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}

fun getNasaApiKey(): String {
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")

    localPropertiesFile.inputStream().use { inputStream ->
        localProperties.load(inputStream)
    }

    return localProperties.getProperty("nasa_api_key") ?: throw IllegalStateException(
        """
        You must specify NASA API key.
        You can specify "DEMO_KEY". However, it's limited to 30 calls per hour and 50 calls per day per IP.
        To get API key: https://api.nasa.gov/
        """.trimIndent()
    )
}
