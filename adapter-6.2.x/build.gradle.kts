plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    compileOnly(Libs.spigot_api)
    compileOnly(Libs.worldguard_legacy)
    implementation(Libs.kotlin_stdlib_jdk8)
    api(project(":adapter-api"))
}
