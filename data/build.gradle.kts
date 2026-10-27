import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.serialization)
    alias(libs.plugins.buildKonfig)
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.isFile) file.inputStream().use(::load)
}
fun apiKey(name: String): String =
    providers.gradleProperty(name).orNull
        ?: providers.environmentVariable(name).orNull
        ?: localProperties.getProperty(name, "")

kotlin {
    android {
        namespace = "com.example.data"
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
            baseName = "data"
            isStatic = true
        }
    }
    sourceSets {
        androidMain.dependencies {
            implementation(libs.cash.sqldelight.android)
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.cash.sqldelight.native)
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            api(projects.domain)
            implementation(projects.shared)
            implementation(libs.cash.sqldelight.coroutines)
            api(libs.cash.sqldelight.runtime)
            implementation(libs.cash.sqldelight.androidx.paging)
            implementation(libs.androidx.paging.common)
            implementation(libs.jetbrains.coroutines)
            implementation(libs.kotlinx.serialization.json)
            api(libs.ktor.client.core)
            implementation(libs.androidx.annotation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.encoding)
            implementation(libs.coil.core)
            implementation(libs.coil.network)
            implementation(libs.squareup.okio.common)
        }
        commonTest.dependencies { implementation(libs.kotlin.test) }
        getByName("androidHostTest").dependencies { implementation(libs.kotlin.test.junit) }
    }
}

sqldelight {
    databases {
        create("LocalDatabase") { packageName.set("com.example.data.database") }
    }
}

// Marvel API credentials are optional for compilation and required for API calls.
buildkonfig {
    packageName = "com.example.data"
    objectName = "ApiKey"
    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "PRIVATE_KEY", apiKey("API_PRIVATE_KEY"))
        buildConfigField(FieldSpec.Type.STRING, "PUBLIC_KEY", apiKey("API_PUBLIC_KEY"))
    }
}
