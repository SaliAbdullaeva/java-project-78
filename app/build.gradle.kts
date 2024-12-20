plugins {
    application
    jacoco // библиотека для анализа покрытия кода тестами
    checkstyle // плагин для проверки кода на соответствие стилевым стандартам
    id("io.freefair.lombok") version "8.6" //плагин уменьшает количество шаблонного кода
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("hexlet.code.Validator")
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.apache.commons:commons-lang3:3.15.0")
    implementation("org.apache.commons:commons-collections4:4.4")

    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.21.0")
}

tasks.test {
    useJUnitPlatform()
}

// Настройка JaCoCo
jacoco {
    toolVersion = "0.8.12"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
