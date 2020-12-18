import de.fayard.refreshVersions.bootstrapRefreshVersions

buildscript {
    repositories { gradlePluginPortal() }
    dependencies.classpath("de.fayard.refreshVersions:refreshVersions:0.9.7")
}

bootstrapRefreshVersions()

gradle.allprojects {
    group = "io.pixeloutlaw.worldguard"

    repositories {
        jcenter()
        maven {
            url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
        }
        maven {
            url = uri("https://maven.sk89q.com/artifactory/repo")
        }
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
    }
}

rootProject.name = "worldguard-adapters"

include(
    "adapter-api",
    "adapter-6.2.x",
    "adapter-7.0.x",
    "adapter-lib"
)
