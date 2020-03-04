import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import nebula.plugin.bintray.BintrayPlugin
import nebula.plugin.responsible.NebulaResponsiblePlugin
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    kotlin("jvm") version Versions.org_jetbrains_kotlin_jvm_gradle_plugin apply false
    id("com.diffplug.gradle.spotless") version Versions.com_diffplug_gradle_spotless_gradle_plugin apply false
    buildSrcVersions
    id("io.gitlab.arturbosch.detekt") version Versions.io_gitlab_arturbosch_detekt_gradle_plugin apply false
    id("org.jetbrains.dokka") version Versions.org_jetbrains_dokka_gradle_plugin
    id("nebula.maven-publish") version Versions.nebula_maven_publish_gradle_plugin apply false
    id("nebula.nebula-bintray") version Versions.nebula_nebula_bintray_gradle_plugin
    id("nebula.project") version Versions.nebula_project_gradle_plugin apply false
    id("nebula.release") version Versions.nebula_release_gradle_plugin
}

subprojects {
    this@subprojects.version = rootProject.version
    pluginManager.withPlugin("java") {
        this@subprojects.pluginManager.apply(NebulaResponsiblePlugin::class.java)
        this@subprojects.pluginManager.apply(BintrayPlugin::class.java)
        this@subprojects.pluginManager.apply(SpotlessPlugin::class.java)

        this@subprojects.tasks.withType<Test> {
            useJUnitPlatform()
        }

        this@subprojects.dependencies {
            "testImplementation"(Libs.spigot_api)
            "testImplementation"(Libs.mockito_core)
            "testImplementation"(Libs.truth)
            "testImplementation"(platform(Libs.junit_bom))
            "testImplementation"("org.junit.jupiter:junit-jupiter")
        }
    }
    pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
        this@subprojects.pluginManager.apply(DetektPlugin::class.java)
        this@subprojects.pluginManager.apply(DokkaPlugin::class.java)
        this@subprojects.pluginManager.apply(SpotlessPlugin::class.java)
        this@subprojects.configure<DetektExtension> {
            baseline = this@subprojects.file("baseline.xml")
        }
        this@subprojects.configure<SpotlessExtension> {
            kotlin {
                target("src/**/*.kt")
                ktlint()
                trimTrailingWhitespace()
                endWithNewline()
                licenseHeaderFile("${rootProject.file("HEADER")}")
            }
        }
        this@subprojects.tasks.withType<KotlinCompile> {
            dependsOn("spotlessKotlinApply")
            kotlinOptions {
                javaParameters = true
                jvmTarget = "1.8"
            }
        }
        this@subprojects.tasks.getByName<DokkaTask>("dokka") {
            outputFormat = "html"
            configuration {
                jdkVersion = 8
            }
        }
        val dokkaJavadoc = this@subprojects.tasks.create("dokkaJavadoc", DokkaTask::class.java) {
            outputDirectory = "${project.buildDir}/javadoc"
            outputFormat = "javadoc"
            configuration {
                jdkVersion = 8
            }
        }
        this@subprojects.tasks.getByName<Jar>("javadocJar") {
            dependsOn(dokkaJavadoc)
        }

        this@subprojects.dependencies {
            "testImplementation"(Libs.kotlin_reflect)
            "testImplementation"(Libs.mockk)
        }
    }
}

bintray {
    pkgName.value("worldguard-adapters")
    repo.value("pixeloutlaw-jars")
    userOrg.value("pixeloutlaw")
    syncToMavenCentral.value(false)
}

tasks.withType<Wrapper> {
    gradleVersion = Versions.gradleLatestVersion
    distributionType = Wrapper.DistributionType.ALL
}

tasks.findByName("release")?.finalizedBy("build")