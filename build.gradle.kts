plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    application
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://github.com/temporalio/sdk-java/releases
    implementation("io.temporal:temporal-sdk:1.29.0")
    implementation("io.temporal:temporal-testing:1.29.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
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
            }
        }
    }
}