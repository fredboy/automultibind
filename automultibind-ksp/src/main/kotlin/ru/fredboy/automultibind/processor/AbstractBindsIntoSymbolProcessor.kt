package ru.fredboy.automultibind.processor

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import kotlin.reflect.KClass

internal abstract class AbstractBindsIntoSymbolProcessor(
    protected val codeGenerator: CodeGenerator,
    protected val logger: KSPLogger,
) : SymbolProcessor {

    protected abstract val processedAnnotationClass: KClass<*>

    protected abstract fun generateModule(
        annotationName: String,
        interfaceName: ClassName,
        moduleName: ClassName,
        classes: List<KSClassDeclaration>
    ): FileSpec?

    private fun processAnnotation(resolver: Resolver, annotation: KSClassDeclaration) {
        val args = annotation.annotations.first {
            it.shortName.getShortName() == processedAnnotationClass.simpleName!!
        }.arguments.takeIf { it.size == 3 } ?: run {
            logger.error("${processedAnnotationClass.simpleName!!} should have 3 arguments")
            throw IllegalArgumentException()
        }

        val interfaceName = args.first { it.name?.getShortName() == "interfaceClass" }.value as KSType
        val modulePackage = args.first { it.name?.getShortName() == "modulePackage" }.value as String
        val moduleName = args.first { it.name?.getShortName() == "moduleName" }.value as String

        val moduleClassName = ClassName(modulePackage, moduleName)
        val elements = resolver.getSymbolsWithAnnotation(annotation.qualifiedName!!.asString())
            .filterIsInstance<KSClassDeclaration>()
            .toList()

        logger.info("Found elements: ${elements.joinToString()}")

        generateModule(
            annotationName = annotation.qualifiedName!!.getShortName(),
            interfaceName = interfaceName.toClassName(),
            moduleName = moduleClassName,
            classes = elements
        )?.writeTo(codeGenerator, Dependencies(true))
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val annotations = resolver.getAnnotatedClasses(processedAnnotationClass.qualifiedName!!, logger)
        logger.info("Found annotations: ${annotations.joinToString { it.qualifiedName!!.asString() }}")
        annotations.forEach { processAnnotation(resolver, it) }
        return emptyList()
    }

}