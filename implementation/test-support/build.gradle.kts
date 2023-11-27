apply(from = "$rootDir/gradle/testing-dependencies.gradle.kts")

dependencies {
    implementation(project(":implementation"))
    implementation(project(":implementation:common"))
}