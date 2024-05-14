package ru.fredboy.automultibind.sample

import ru.fredboy.automultibind.sample.obj.IMyInterface
import javax.inject.Inject

class MyClass @Inject constructor(
    private val set: Set<@JvmSuppressWildcards IMyInterface>,
    private val map: Map<String, @JvmSuppressWildcards IMyInterface>,
) {

    fun printSet() {
        println("set: $set")
    }

    fun printMap() {
        println("map: $map")
    }

}