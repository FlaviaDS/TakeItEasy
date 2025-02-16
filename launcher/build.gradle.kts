plugins {
    id("java")
    id("application")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":classic"))
    implementation(project(":hexagonal"))
}

application {
    mainClass.set("org.example.Launcher")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

