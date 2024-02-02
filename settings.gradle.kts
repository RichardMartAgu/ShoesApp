pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                username = "mapbox"
                password = "sk.eyJ1IjoicmljaGFyZG1hcnQiLCJhIjoiY2xxc2wwZGRqMXV4ajJrc2JtbzNzb3N0MiJ9.Ar39zcLxVDSmEbzam-eJFA"
            }
        }
    }
}
rootProject.name = "ShoesApp"
include(":app")
