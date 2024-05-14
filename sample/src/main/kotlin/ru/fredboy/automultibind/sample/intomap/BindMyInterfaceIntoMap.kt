package ru.fredboy.automultibind.sample.intomap

import ru.fredboy.automultibind.annotations.BindsIntoMapStringKey
import ru.fredboy.automultibind.sample.config.AutomultibindConfig
import ru.fredboy.automultibind.sample.obj.IMyInterface

@BindsIntoMapStringKey(
    interfaceClass = IMyInterface::class,
    modulePackage = AutomultibindConfig.MODULE_PACKAGE,
    moduleName = "MyInterfaceIntoMapModule"
)
annotation class BindMyInterfaceIntoMap(val stringKey: String)
