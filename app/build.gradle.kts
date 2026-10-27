import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.devtoolsKsp)
}

kotlin {
    android {
        namespace = "com.example.kmp"
        compileSdk {
            version = release(libs.versions.android.compileSdk.get().toInt()) {
                minorApiLevel = libs.versions.android.compileSdkMinor.get().toInt()
            }
        }
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
        androidResources { enable = true }
    }
    listOf(iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "app"
            isStatic = true
        }
    }
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.startup)
        }
        commonMain.dependencies {
            api(libs.compose.runtime)
            implementation(libs.compose.ui)
            implementation(libs.compose.material3)
            implementation(libs.multiplatform.navigation.compose)
            implementation(libs.multiplatform.lifecycle.viewmodel.compose)
            implementation(libs.coil.core)
            implementation(libs.coil.network)
            implementation(libs.coil.compose)
            implementation(projects.feature.main)
            implementation(projects.feature.bookmark)
            implementation(projects.data)
            implementation(projects.domain)
            implementation(projects.shared)
        }
    }
}

dependencies {
    add("kspAndroid", libs.tatarka.inject.compiler)
    add("kspIosArm64", libs.tatarka.inject.compiler)
    add("kspIosSimulatorArm64", libs.tatarka.inject.compiler)
}
