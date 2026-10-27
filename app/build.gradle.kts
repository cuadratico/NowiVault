import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.nothingsecure"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.nothingsecure"
        minSdk = 29
        targetSdk = 36
        versionCode = 37
        versionName = "1.4.23-Castlevania"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val keystorePath = System.getenv("keyStore")
    if (keystorePath != null && file(keystorePath).exists()) {
        signingConfigs {
            create("r_NowiVault") {
                storeFile = file(keystorePath)
                storePassword = System.getenv("ks_pass")
                keyAlias = System.getenv("ks_alias")
                keyPassword = System.getenv("a_pass")
            }
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
            vendor.set(JvmVendorSpec.ADOPTIUM)
        }
    }

    buildTypes {
        release {
            val signingConf = signingConfigs.findByName("r_NowiVault")
            if (signingConf != null) {
                signingConfig = signingConf
            }

            isMinifyEnabled = true
            isShrinkResources = true
            optimization {
                enable = true
            }

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

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
}

dependencies {

    implementation(libs.lottie)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.biometric)
    implementation (libs.androidx.work.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}