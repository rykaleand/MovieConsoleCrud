plugins {
    id("java")
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "ru.movie"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

    // Validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // OpenAPI / Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // PostgreSQL
    runtimeOnly("org.postgresql:postgresql")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    
    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Mockito
    testImplementation("org.mockito:mockito-core:5.3.1")

    // RestAssured
    testImplementation("io.rest-assured:rest-assured:5.5.1")

    // Selenium
    testImplementation("org.seleniumhq.selenium:selenium-java:4.10.0")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.5.3")
}

tasks.test {
    useJUnitPlatform()
}