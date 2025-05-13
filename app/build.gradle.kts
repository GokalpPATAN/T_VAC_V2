import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

fun getLocalProperty(key: String): String {
    val props = Properties()
    props.load(rootProject.file("local.properties").inputStream())
    return props.getProperty(key)
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    alias(libs.plugins.google.gms.google.services)


    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)

    id("kotlin-parcelize")



}

android {
    namespace = "com.farukayata.t_vac_kotlin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.farukayata.t_vac_kotlin"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        //gemini

        val geminiKey = localProperties.getProperty("GEMINI_API_KEY") ?: ""
        buildConfigField("String", "GEMINI_API_KEY", "\"$geminiKey\"")
        resValue("string", "google_maps_key", "\"$geminiKey\"")//maps için

        //buildConfigField("String", "GEMINI_API_KEY", "\"${project.properties["GEMINI_API_KEY"]}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "OPENAI_API_KEY", "\"${getLocalProperty("OPENAI_API_KEY")}\"")
        }
        release {
            isMinifyEnabled = false
            buildConfigField("String", "OPENAI_API_KEY", "\"${getLocalProperty("OPENAI_API_KEY")}\"")
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
    buildFeatures {
        viewBinding = true
        //gemini ay
        buildConfig = true



    }
}
dependencies {

    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)



    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)


    implementation ("androidx.activity:activity-ktx:1.6.0")




    //Nav
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    //Gson
    implementation ("com.google.code.gson:gson:2.10.1")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    //OkHttp
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    // Glide
    //implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")

    val paging_version = "3.3.1"
    implementation ("androidx.paging:paging-runtime-ktx:$paging_version")

    //firebase
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.firebase:firebase-storage:21.0.0")

    //chucker
    implementation("com.github.chuckerteam.chucker:library:4.0.0")

    //checkbox
    implementation("com.google.android.material:material:1.9.0")

    //swiperefresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //await için
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.0")

    // Gemini SDK
    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")

    //cardmodel
    implementation ("androidx.cardview:cardview:1.0.0")

    //maps
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")




}

kapt {
    correctErrorTypes = true
}