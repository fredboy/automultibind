# AutoMultiBind
My simple KSP processor that generates dagger modules with multibindings

## Setup
* Clone
* Run `./gradlew publishToMavenLocal`
* In your project, enable KSP gradle plugin
* Add `mavenLocal()` to repositories in build.gradle
* Add dependencies
```
    implementation "ru.fredboy:automultibind-annotations:1.0.1"
    ksp "ru.fredboy:automultibind-ksp:1.0.1"
```

Or you could use my maven repo, if it is up
```
repositories {
    maven { url = uri("https://mvn.fredboy.ru/releases") }
}

dependencies {
    implementation("ru.fredboy:automultibind-annotations:1.0.1")
    ksp("ru.fredboy:automultibind-ksp:1.0.1")
}
```

## How It Works
For usage example see [sample module](https://github.com/fredboy/automultibind/blob/master/sample)

Instead of manually writing:

```kotlin
@Module
abstract class MyInterfaceIntoSetModule {

    @Binds @IntoSet
    abstract fun bindMyImplementation1(impl: MyImplementation1): IMyInterface

    @Binds @IntoSet
    abstract fun bindMyImplementation2(impl: MyImplementation2): IMyInterface
}
```

You can simply annotate your implementations:

```kotlin
@BindMyInterfaceIntoSet
@BindMyInterfaceIntoMap(stringKey = MyImplementation1.KEY)
class MyImplementation1 @Inject constructor() : IMyInterface {

    override fun getString() = "My implementation #1"

    companion object {
        const val KEY = "MyImplementation1_Key"
    }
}
```

The processor scans for these annotations and generates all the necessary Dagger modules.

---

## Step-by-Step Usage

### 1. Create Your Interface

```kotlin
interface IMyInterface {
    fun getString(): String
}
```

---

### 2. Create Binding Annotations

For example, for `IntoSet` bindings:

```kotlin
@BindsIntoSet(
    interfaceClass = IMyInterface::class,
    modulePackage = AutomultibindConfig.MODULE_PACKAGE,
    moduleName = "MyInterfaceIntoSetModule"
)
annotation class BindMyInterfaceIntoSet
```

For `IntoMap` bindings with `StringKey`:

```kotlin
@BindsIntoMapStringKey(
    interfaceClass = IMyInterface::class,
    modulePackage = AutomultibindConfig.MODULE_PACKAGE,
    moduleName = "MyInterfaceIntoMapModule"
)
annotation class BindMyInterfaceIntoMap(val stringKey: String)
```

---

### 3. Annotate Your Implementations

```kotlin
@BindMyInterfaceIntoSet
@BindMyInterfaceIntoMap(stringKey = MyImplementation2.KEY)
class MyImplementation2 @Inject constructor() : IMyInterface {

    override fun getString() = "My implementation #2"

    companion object {
        const val KEY = "MyImplementation2_Key"
    }
}
```

---

### 4. Use the Generated Modules in Your Component

The processor will generate modules like:

```kotlin
@Module
@Generated(value = ["ru.fredboy.automultibind.processor.BindsIntoSetSymbolProcessor"])
abstract class MyInterfaceIntoSetModule {

    @Binds @IntoSet
    abstract fun bindMyImplementation1(impl: MyImplementation1): IMyInterface

    @Binds @IntoSet
    abstract fun bindMyImplementation2(impl: MyImplementation2): IMyInterface
}
```

```kotlin
@Module
@Generated(value = ["ru.fredboy.automultibind.processor.BindsIntoMapStringKeySymbolProcessor"])
abstract class MyInterfaceIntoMapModule {

    @Binds @IntoMap @StringKey("MyImplementation1_Key")
    abstract fun bindMyImplementation1(impl: MyImplementation1): IMyInterface

    @Binds @IntoMap @StringKey("MyImplementation2_Key")
    abstract fun bindMyImplementation2(impl: MyImplementation2): IMyInterface
}
```

Then you just include them in your Dagger component:

```kotlin
@Component(
    modules = [
        MyInterfaceIntoSetModule::class,
        MyInterfaceIntoMapModule::class
    ]
)
interface MyComponent {
    fun getMyClass(): MyClass
}
```

---

## Benefits

- **Zero boilerplate**: Just annotate your implementation, and modules are generated.
- **Type-safe**: Module signatures are generated based on your actual types.
- **Consistent**: All modules follow the same style and conventions.
- **Flexible**: Works for both `IntoSet` and `IntoMap` bindings (string keys).

---
