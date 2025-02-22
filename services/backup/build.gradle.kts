plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = sdk.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = sdk.versions.minSdk.get().toInt()
        targetSdk = sdk.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
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
    namespace = "de.mm20.launcher2.backup"
}

dependencies {
    implementation(libs.bundles.kotlin)
    implementation(libs.androidx.core)

    implementation(libs.koin.android)

    implementation(project(":data:searchable"))
    implementation(project(":data:widgets"))
    implementation(project(":data:search-actions"))
    implementation(project(":core:preferences"))
    implementation(project(":core:ktx"))
    implementation(project(":data:customattrs"))
    implementation(project(":data:themes"))

}