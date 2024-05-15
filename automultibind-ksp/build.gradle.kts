import java.io.FileInputStream
import java.io.IOException
import java.util.*

plugins {
    `maven-publish`
    id("kotlin")
    id("com.google.devtools.ksp") version Versions.KSP
    signing
}

kotlin {
    jvmToolchain(17)
}

tasks {
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }
}

val mavenCredentials = Properties()
try {
    mavenCredentials.load(FileInputStream(rootProject.file("credentials.properties")))
} catch (_: IOException) {}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            artifactId = "automultibind-ksp"
            version = project.version as String

            from(components["java"])
            artifact(tasks["sourcesJar"]) {
                classifier = "sources"
            }

            pom {
                name = "AutoMultiBind KSP Processor"
                description = "Simple processors for generating dagger multibinding modules"
                url = "https://github.com/fredboy/automultibind"

                licenses {
                    license {
                        name = "MIT License"
                        url = "https://github.com/fredboy/automultibind/blob/master/LICENSE"
                    }
                }

                developers {
                    developer {
                        id = "fredboy"
                        name = "Fedor Ivanov"
                        email = "fredboy@protonmail.com"
                    }
                }
            }
        }
    }

    repositories {
        maven {
            url = uri("https://mvn.fredboy.ru/releases")
            name = "fredboyRepository"

            credentials {
                username = mavenCredentials["mavenUsername"] as? String
                password = mavenCredentials["mavenPassword"] as? String
            }
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}


dependencies {
    implementation(project(":automultibind-annotations"))
    implementation("com.squareup:kotlinpoet-ksp:${Versions.KOTLIN_POET_KSP}")
    implementation("com.google.devtools.ksp:symbol-processing-api:${Versions.KSP}")
}
