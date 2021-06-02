plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.5.0"
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:_")

    api(project(":adapter-6.2.x"))
    api(project(":adapter-7.0.x"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("io.pixeloutlaw:kindling:_")
}
