plugins {
    kotlin("jvm") version "1.4.21" apply false
    id("nebula.nebula-bintray") version "8.5.0"
    id("nebula.release") version "15.3.0"
    id("io.pixeloutlaw.multi") version "0.6.0"
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
    gradleVersion = "6.7.1"
    distributionType = Wrapper.DistributionType.ALL
}
