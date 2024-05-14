package ru.fredboy.automultibind.sample

import ru.fredboy.automultibind.sample.di.DaggerMyComponent
import ru.fredboy.automultibind.sample.di.MyComponent

fun main() {
    val myComponent: MyComponent = DaggerMyComponent.builder().build();
    val myClass = myComponent.getMyClass()

    myClass.printSet()
    myClass.printMap()
}