import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    android {
        namespace = "com.example.core.ui"
        compileSdk {
            version = release(libs.versions.android.compileSdk.get().toInt()) {
                minorApiLevel = libs.versions.android.compileSdkMinor.get().toInt()
            }
        }
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
        androidResources { enable = true }
    }
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(libs.compose.runtime)
            api(libs.compose.ui)
            api(libs.compose.material3)
            api(libs.compose.components.resources)
            implementation(libs.compose.foundation)
            implementation(libs.coil.compose)
            implementation(projects.domain)
            implementation(projects.shared)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.example.core.ui.resources"
}

// The default metadata name, ui_commonMain, collides with Compose UI's library.
tasks.withType<KotlinCompileCommon>().configureEach {
    compilerOptions.freeCompilerArgs.add("-module-name=core-ui_$name")
}
