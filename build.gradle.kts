plugins {
    kotlin("jvm") apply false
    id("nebula.nebula-bintray")
    id("nebula.release")
    id("io.pixeloutlaw.multi")
}

subprojects {
    this@subprojects.version = rootProject.version
}

bintray {
    pkgName.value("worldguard-adapters")
    repo.value("pixeloutlaw-jars")
    userOrg.value("pixeloutlaw")
    syncToMavenCentral.value(false)
}

tasks.withType<Wrapper> {
    gradleVersion = "6.8.1"
    distributionType = Wrapper.DistributionType.ALL
}
