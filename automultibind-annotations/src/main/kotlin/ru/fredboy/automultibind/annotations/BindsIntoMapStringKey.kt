package ru.fredboy.automultibind.annotations

import kotlin.reflect.KClass

/**
 * Annotation annotated with this must include stringKey parameter for key selection in generated module
 *
 * @param interfaceClass an interface to bind implementations to
 * @param modulePackage a package for generated module
 * @param moduleName a module name, that you will include in your dagger component
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class BindsIntoMapStringKey(
    val interfaceClass: KClass<*>,
    val modulePackage: String,
    val moduleName: String,
)
