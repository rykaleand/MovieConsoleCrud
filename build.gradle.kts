plugins {
    id("java")
}

group = "ru.unosoft"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.actuator)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

tasks.test {
    useJUnitPlatform()
}