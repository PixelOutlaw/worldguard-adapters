plugins {
    kotlin("jvm") version "1.5.10" apply false
    id("io.pixeloutlaw.gradle")
}

description = "WorldGuard Adapters for MythicDrops"

subprojects {
    this@subprojects.description = rootProject.description
    this@subprojects.version = rootProject.version
}
