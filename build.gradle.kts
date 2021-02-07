plugins {
    kotlin("jvm") apply false
    id("io.pixeloutlaw.gradle")
}

subprojects {
    this@subprojects.version = rootProject.version
}
