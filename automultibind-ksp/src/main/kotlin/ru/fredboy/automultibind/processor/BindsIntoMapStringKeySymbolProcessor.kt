package ru.fredboy.automultibind.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import ru.fredboy.automultibind.annotations.BindsIntoMapStringKey
import kotlin.reflect.KClass

internal class BindsIntoMapStringKeySymbolProcessor(
    codeGenerator: CodeGenerator,
    logger: KSPLogger,
) : AbstractBindsIntoSymbolProcessor(codeGenerator, logger) {

    override val processedAnnotationClass: KClass<*>
        get() = ANNOTATION_CLASS

    override fun generateModule(
        annotationName: String,
        interfaceType: KSType,
        moduleName: ClassName,
        classes: List<KSClassDeclaration>
    ): FileSpec? {
        if (classes.isEmpty()) {
            return null
        }

        val bindings = classes.map { decl ->
            val stringKey = decl.annotations.first { declAnn ->
                declAnn.shortName.getShortName() == annotationName
            }.arguments.firstOrNull { arg ->
                arg.name!!.getShortName() == "stringKey"
            }?.value as? String ?: run {
                logger.error("@${annotationName} must include stringKey parameter for key selection in generated module")
                throw IllegalArgumentException()
            }

            val clazz = decl.toClassName()

            FunSpec.builder("bind${clazz.simpleName}")
                .addAnnotation(ClassName("dagger", "Binds"))
                .addAnnotation(ClassName("dagger.multibindings", "IntoMap"))
                .addAnnotation(
                    AnnotationSpec.builder(ClassName("dagger.multibindings", "StringKey"))
                        .addMember("\"$stringKey\"")
                        .build()
                )
                .addParameter(ParameterSpec("impl", clazz))
                .returns(interfaceType.toTypeName())
                .addModifiers(KModifier.ABSTRACT)
                .build()
        }

        val moduleObject = TypeSpec.classBuilder(moduleName)
            .addModifiers(KModifier.ABSTRACT)
            .addAnnotation(ClassName("dagger", "Module"))
            .addAnnotation(
                AnnotationSpec.builder(ClassName("javax.annotation.processing", "Generated"))
                    .addMember("value = [%S]", this::class.qualifiedName!!)
                    .build()
            )
            .addFunctions(bindings)
            .build()

        return FileSpec.builder(moduleName)
            .addType(moduleObject)
            .build()

    }

    companion object {
        private val ANNOTATION_CLASS = BindsIntoMapStringKey::class
    }

}
