plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    application
}

group = "xyz.block.simpletemporalworkflow"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

val temporalVersion = "1.20.0"

dependencies {
    // https://github.com/temporalio/sdk-java/releases, https://central.sonatype.com/artifact/io.temporal/temporal-sdk?smo=true
    implementation("io.temporal:temporal-sdk:${temporalVersion}")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("xyz.block.simpletemporalworkflow.MainKt")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.13.1") // Using JUnit Jupiter
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test:1.9.22")
                implementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.22")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
                implementation("io.mockk:mockk:1.13.9")
                implementation("io.temporal:temporal-testing:${temporalVersion}")
            }
        }
    }
}