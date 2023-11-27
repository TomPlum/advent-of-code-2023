apply(from = "$rootDir/gradle/testing-dependencies.gradle.kts")
apply(from = "$rootDir/gradle/logging-dependencies.gradle.kts")

dependencies {
    implementation(project(":implementation"))

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.16.0")
}