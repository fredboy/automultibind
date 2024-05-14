package ru.fredboy.automultibind.sample.intoset

import ru.fredboy.automultibind.annotations.BindsIntoSet
import ru.fredboy.automultibind.sample.config.AutomultibindConfig
import ru.fredboy.automultibind.sample.obj.IMyInterface

@BindsIntoSet(
    interfaceClass = IMyInterface::class,
    modulePackage = AutomultibindConfig.MODULE_PACKAGE,
    moduleName = "MyInterfaceIntoSetModule"
)
annotation class BindMyInterfaceIntoSet
