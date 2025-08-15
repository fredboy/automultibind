package ru.fredboy.automultibind.annotations

import kotlin.reflect.KClass

/**
 * @param interfaceClass an interface to bind implementations to
 * @param modulePackage a package for generated module
 * @param moduleName a module name, that you will include in your dagger component
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class BindsIntoSet(
    val interfaceClass: KClass<*>,
    val modulePackage: String,
    val moduleName: String,
)
