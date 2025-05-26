pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.android.application") {
                useVersion("8.2.0") // Or your desired version
            }
            if (requested.id.id == "org.jetbrains.kotlin.android") {
                useVersion("1.9.20") // Or your desired version
            }
            // ... other plugins
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "YourProjectName"
include(":app") // Or your module name