import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
}

kotlin {
    android {
        namespace = "com.example.shared"
        compileSdk {
            version = release(libs.versions.android.compileSdk.get().toInt()) {
                minorApiLevel = libs.versions.android.compileSdkMinor.get().toInt()
            }
        }
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
        withHostTest {}
    }
    listOf(iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }
    sourceSets {
        commonMain.dependencies {
            api(libs.jetbrains.coroutines)
            implementation(libs.crypto.md)
            api(libs.squareup.okio.common)
            api(libs.aakira.napier)
            api(libs.kotlin.datetime)
            api(libs.tatarka.inject.runtime)
        }
        commonTest.dependencies { implementation(libs.kotlin.test) }
        getByName("androidHostTest").dependencies { implementation(libs.kotlin.test.junit) }
        androidMain.dependencies {
            implementation(libs.androidx.startup)
            implementation(libs.androidx.lifecycle.runtime.ktx)
            implementation(libs.androidx.lifecycle.viewmodel.savedstate)
            implementation(libs.androidx.annotation.jvm)
        }
        iosMain.dependencies { implementation(libs.ionspin.bignum) }
    }
}
