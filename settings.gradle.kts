rootProject.name = "worldguard-adapters"

gradle.allprojects {
    group = "io.pixeloutlaw.worldguard"

    repositories {
        mavenCentral()
        jcenter()
        maven {
            url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
        }
        maven {
            url = uri("http://maven.sk89q.com/artifactory/repo")
        }
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
    }
}

include(
    "adapter-api",
    "adapter-6.2.x",
    "adapter-7.0.x",
    "adapter-lib"
)
