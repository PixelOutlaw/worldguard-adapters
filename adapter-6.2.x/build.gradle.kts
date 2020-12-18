plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:_")
    compileOnly("com.sk89q.worldguard:worldguard-legacy:_")

    api(project(":adapter-api"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:_")
}
