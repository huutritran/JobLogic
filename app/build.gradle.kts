plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = ConfigData.compileSdkVersion
    buildToolsVersion = ConfigData.buildToolsVersion

    defaultConfig {
        applicationId = "com.example.joblogic"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = ConfigData.testInstrumentationRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

dependencies {
    implementation(Deps.kotlin)

    implementation(Deps.hilt)
    kapt(Deps.hiltCompiler)

    implementation(Deps.ktx)
    implementation(Deps.fragmentKtx)
    implementation(Deps.androidLifeCycleExt)

    implementation(Deps.coroutine)
    implementation(Deps.androidCoroutine)

    implementation(Deps.retrofit)
    implementation(Deps.retrofitGsonConverter)
    implementation(Deps.retrofitCoroutineAdapter)

    implementation(Deps.appCompat)
    implementation(Deps.material)
    implementation(Deps.constraintLayout)

    testImplementation(Deps.jUnit)
    testImplementation(Deps.mockk)
    testImplementation(Deps.mockkCommon)
    testImplementation(Deps.kotest)
    androidTestImplementation(Deps.androidJUnit)
    androidTestImplementation(Deps.espresso)
}

kapt {
    correctErrorTypes = true
}