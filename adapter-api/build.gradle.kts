plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.5.0"
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:_")
    implementation(kotlin("stdlib-jdk8"))
}
