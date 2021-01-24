plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:_")

    api(project(":adapter-6.2.x"))
    api(project(":adapter-7.0.x"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:_")
    implementation("saschpe.log4k:log4k-jvm:_")
}
