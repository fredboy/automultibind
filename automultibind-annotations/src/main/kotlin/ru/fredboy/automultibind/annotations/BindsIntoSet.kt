package ru.fredboy.automultibind.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class BindsIntoSet(
    val interfaceClass: KClass<*>,
    val modulePackage: String,
    val moduleName: String,
)
