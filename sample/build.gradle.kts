plugins {
    id("kotlin")
    id("com.google.devtools.ksp") version Versions.KSP
}

tasks {
    val run by creating(JavaExec::class) {
        dependsOn("kspKotlin")
        mainClass = "ru.fredboy.automultibind.sample.MainKt"
        classpath = sourceSets["main"].runtimeClasspath
    }
}

repositories {
    maven { url = uri("https://mvn.fredboy.ru/releases") }
}

dependencies {
    implementation("ru.fredboy:automultibind-annotations:1.0.0")
    ksp("ru.fredboy:automultibind-ksp:1.0.0")
}

dependencies {
    api("com.google.dagger:dagger:${Versions.DAGGER}")
    ksp("com.google.dagger:dagger-compiler:${Versions.DAGGER}")
}

