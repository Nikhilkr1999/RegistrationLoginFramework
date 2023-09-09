plugins {
	java
	id("org.springframework.boot") version "3.1.0"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "com.humanassist"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")
	implementation ("com.fasterxml.jackson.core:jackson-databind:2.12.5") // Core Jackson functionality
	implementation ("com.fasterxml.jackson.core:jackson-core:2.12.5") // Core Jackson functionality
	implementation("com.fasterxml.jackson.core:jackson-annotations:2.12.5") // Jackson annotations (optional)
	implementation("org.codehaus.jackson:jackson-core-asl:1.9.13") // Jackson core functionality (optional)
	implementation("org.codehaus.jackson:jackson-mapper-asl:1.9.13")
	implementation("org.flywaydb:flyway-core")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
