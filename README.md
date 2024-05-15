# AutoMultiBind
My simple KSP processor that generates dagger modules with multibindings

### Setup
* Clone
* Run `./gradlew publishToMavenLocal`
* In your project, enable KSP gradle plugin
* Add `mavenLocal()` to repositories in build.gradle
* Add dependencies
```
    implementation "ru.fredboy:automultibind-annotations:1.0.0"
    ksp "ru.fredboy:automultibind-ksp:1.0.0"
```

Or you could use my maven repo, if it is up
```
repositories {
    maven { url = uri("https://mvn.fredboy.ru/releases") }
}

dependencies {
    implementation("ru.fredboy:automultibind-annotations:1.0.0")
    ksp("ru.fredboy:automultibind-ksp:1.0.0")
}
```

### Usage
For usage example see [sample module](https://github.com/fredboy/automultibind/blob/master/sample)
