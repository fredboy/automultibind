package ru.fredboy.automultibind.annotations

import kotlin.reflect.KClass

/**
 * Annotation annotated with this must include stringKey parameter for key selection in generated module
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class BindsIntoMapStringKey(
    val interfaceClass: KClass<*>,
    val modulePackage: String,
    val moduleName: String,
)
