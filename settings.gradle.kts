pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ComposeMultiplatform"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":androidApp", ":app", ":shared", ":data", ":domain")
include(":core:ui", ":feature:main", ":feature:bookmark")
