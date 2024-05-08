import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.serialization)
    alias(libs.plugins.buildKonfig)
//    alias(libs.plugins.ktor)
}

val localProperties = gradleLocalProperties(rootDir)

fun getLocalProperties(key: String): String =
    localProperties.getProperty(key)

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "data"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.cash.sqldelight.android)
//            implementation(libs.cash.sqldelight.androidx.paging)
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.cash.sqldelight.native)
            implementation(libs.ktor.client.darwin)
        }
//        jvmMain.dependencies {
//            implementation(libs.cash.sqldelight.jvm)
//        }
        commonMain.dependencies {
            implementation(libs.cash.sqldelight.coroutines)
            implementation(libs.cash.sqldelight.androidx.paging)
            implementation(libs.cash.paging.compose.common)
            implementation(libs.cash.paging.common)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.encoding)
            implementation(libs.coil.core)
            implementation(libs.coil.network)
            implementation(libs.coil.compose)
            implementation(libs.touchlab.stately.common)
            implementation(libs.touchlab.stately.isolate)
            implementation(libs.touchlab.stately.collections)
            implementation(projects.shared)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
    task("testClasses")
}

android {
    namespace = "com.example.data"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildToolsVersion = "34.0.0"
    ndkVersion = "23.1.7779620"
    dependencies {
        implementation(project(":shared"))
    }
}

sqldelight {
    databases {
        create("LocalDatabase") {
            packageName.set("com.example.data.database")
        }
    }
}

/**
 * Access the https://developer.marvel.com/ and sign up for your account
 * Get your PRIVATE_KEY AND PUBLIC_KEY from 'Get Key' Menu
 * Set your key in local.properties file
 *
 * local.properties
 * API_PRIVATE_KEY = {Your private key}
 * API_PUBLIC_KEY = {Your public key}
 */
buildkonfig {
    packageName = "com.example.data"
    objectName = "ApiKey"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "PRIVATE_KEY", getLocalProperties("API_PRIVATE_KEY"))
        buildConfigField(FieldSpec.Type.STRING, "PUBLIC_KEY", getLocalProperties("API_PUBLIC_KEY"))
    }
}