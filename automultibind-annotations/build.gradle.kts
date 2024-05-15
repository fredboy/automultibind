import java.io.FileInputStream
import java.util.*

plugins {
    `maven-publish`
    id("kotlin")
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
mavenCredentials.load(FileInputStream(rootProject.file("credentials.properties")))

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            artifactId = "automultibind-annotations"
            version = project.version as String

            from(components["java"])
            artifact(tasks["sourcesJar"]) {
                classifier = "sources"
            }

            pom {
                name = "AutoMultiBind Annotations"
                description = "Annotations for my automultibind KSP processor"
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
                username = mavenCredentials["mavenUsername"] as String
                password = mavenCredentials["mavenPassword"] as String
            }
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}
