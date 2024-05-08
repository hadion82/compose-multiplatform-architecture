plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.devtoolsKsp)
}

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
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.jetbrains.coroutines)
            implementation(libs.crypto.md)
            implementation(libs.squareup.okio.common)
            api(libs.aakira.napier)
            api(libs.kotlin.datetime)
            api(libs.tatarka.inject.runtime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.androidx.startup)
            implementation(libs.androidx.lifecycle.runtime.ktx)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)

//            implementation(libs.jetbrains.coroutines)
        }
        iosMain.dependencies {
            implementation(libs.ionspin.bignum)
        }

//        jvmMain.dependencies {
//            implementation(libs.squareup.okio.nodefilesystem)
//        }
    }
    task("testClasses")
}

android {
    namespace = "com.example.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
dependencies {
    implementation(libs.androidx.annotation.jvm)
    add("kspIosX64", libs.tatarka.inject.comiler)
    add("kspIosArm64", libs.tatarka.inject.comiler)
    add("kspIosSimulatorArm64", libs.tatarka.inject.comiler)
    ksp(libs.tatarka.inject.comiler)
}
