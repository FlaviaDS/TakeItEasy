plugins {
    id("java")
    id("application")
}

repositories {
    mavenCentral()
}

dependencies {
    // Gestione delle versioni di JUnit 5
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

application {
    // Imposta la main class della versione classic
    mainClass.set("org.example.classic.view.GameBoardUI")
}


