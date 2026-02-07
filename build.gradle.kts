plugins {
    java
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.backend.proyecto"
version = "0.0.1-SNAPSHOT"
description = "Proyecto project for Spring Boot"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

// Habilitar nombres de parámetros para reflexión (Spring)
tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

repositories {
    mavenCentral()
}

dependencies {
    // =========================
    // CORE SPRING
    // =========================
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // =========================
    // DATABASE
    // =========================
    runtimeOnly("org.postgresql:postgresql")

    // =========================
    // JWT
    // =========================
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")

    // =========================
    // EMAIL
    // =========================
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // =========================
    // VALIDATION
    // =========================
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // =========================
    // LOMBOK
    // =========================
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // =========================
    // ACTUATOR (health / metrics)
    // =========================
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // =========================
    // SWAGGER / OPENAPI
    // =========================
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // =========================
    // CLOUDINARY
    // =========================
    implementation("com.cloudinary:cloudinary-http44:1.35.0")

    // =========================
    // PDF GENERATION
    // =========================
    implementation("com.github.librepdf:openpdf:1.3.30")

    // =========================
    // DEVTOOLS
    // =========================
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // =========================
    // TESTING
    // =========================
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}