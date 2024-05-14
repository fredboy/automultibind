package ru.fredboy.automultibind.processor

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate

internal fun Resolver.getAnnotatedClasses(
    annotation: String,
    logger: KSPLogger,
): Sequence<KSClassDeclaration> {
    logger.info("Resolving annotation $annotation")
    return getSymbolsWithAnnotation(annotation)
        .filterIsInstance<KSClassDeclaration>()
        .filter(KSNode::validate)
}