buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
    }
}

allprojects {
    group = "ru.fredboy"
    version = "1.0.2"

    repositories {
        mavenCentral()
        mavenLocal()
    }
}



