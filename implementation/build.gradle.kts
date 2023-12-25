import io.gitlab.arturbosch.detekt.Detekt

apply(from = "$rootDir/gradle/testing-dependencies.gradle.kts")
apply(from = "$rootDir/gradle/logging-dependencies.gradle.kts")

plugins {
    jacoco
    id("io.gitlab.arturbosch.detekt").version("1.23.4")
}

dependencies {
    implementation(project(":implementation:common"))
    testImplementation(project(":implementation:test-support"))

    implementation("tools.aqua:z3-turnkey:4.12.2.1")
    implementation("org.jgrapht:jgrapht-core:1.5.2")

    testImplementation("io.mockk:mockk:1.13.8")
}

subprojects {
    apply(plugin = "jacoco")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    tasks.test {
        finalizedBy(tasks.jacocoTestReport)
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test)
    }

    jacoco {
        toolVersion = "0.8.11"
        reportsDirectory.set(file("$buildDir/reports"))
    }

    tasks.jacocoTestReport {
        group = "Reporting"
        description = "Generate Jacoco test coverage report"

        reports {
            xml.required.set(true)
            html.required.set(true)
            csv.required.set(false)
        }
    }

    tasks.jacocoTestCoverageVerification  {
        violationRules {
            rule {
                limit {
                    minimum = "0.9".toBigDecimal()
                }
            }
        }
    }
}

tasks.withType<Detekt>().configureEach {
    reports {
        html {
            debug = false
            required.set(true)
            ignoreFailures = false
            buildUponDefaultConfig = false
            outputLocation.set(file("$buildDir/reports/detekt/report.html"))
            baseline.set(file("$projectDir/src/main/resources/baseline.xml"))
            config.setFrom(files("$projectDir/src/main/resources/detekt-config.yml"))
        }
        xml {
            required.set(false)
        }
        txt {
            required.set(false)
        }
    }
}