pluginManagement {
    repositories {
        mavenCentral()
        google()
        maven {
            url = uri("https://jitpack.io") // Correct way to define the URL
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

rootProject.name = "MovieApp"
include(":app")
 