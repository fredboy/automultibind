package ru.fredboy.automultibind.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.toClassName
import ru.fredboy.automultibind.annotations.BindsIntoSet
import kotlin.reflect.KClass

internal class BindsIntoSetSymbolProcessor(
    codeGenerator: CodeGenerator,
    logger: KSPLogger,
) : AbstractBindsIntoSymbolProcessor(codeGenerator, logger) {

    override val processedAnnotationClass: KClass<*>
        get() = ANNOTATION_CLASS

    override fun generateModule(
        annotationName: String,
        interfaceName: ClassName,
        moduleName: ClassName,
        classes: List<KSClassDeclaration>
    ): FileSpec? {
        if (classes.isEmpty()) {
            return null
        }

        val bindings = classes.map { decl ->
            val clazz = decl.toClassName()

            FunSpec.builder("bind${clazz.simpleName}")
                .addAnnotation(ClassName("dagger", "Binds"))
                .addAnnotation(ClassName("dagger.multibindings", "IntoSet"))
                .addParameter(ParameterSpec("impl", clazz))
                .returns(interfaceName)
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
        private val ANNOTATION_CLASS = BindsIntoSet::class
    }

}
