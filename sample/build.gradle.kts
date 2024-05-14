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

dependencies {
    api("com.google.dagger:dagger:${Versions.DAGGER}")

    implementation(project(":automultibind-annotations"))
    ksp(project(":automultibind-ksp"))

    ksp("com.google.dagger:dagger-compiler:${Versions.DAGGER}")
}

