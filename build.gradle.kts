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
    version = "1.0.1"

    repositories {
        mavenCentral()
        mavenLocal()
    }
}



