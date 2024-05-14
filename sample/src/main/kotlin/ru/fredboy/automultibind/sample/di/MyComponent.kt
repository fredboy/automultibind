package ru.fredboy.automultibind.sample.di

import dagger.Component
import ru.fredboy.automultibind.sample.MyClass
import ru.fredboy.automultibind.sample.generated.module.*

@Component(modules = [MyInterfaceIntoSetModule::class, MyInterfaceIntoMapModule::class])
interface MyComponent {

    fun getMyClass(): MyClass

}