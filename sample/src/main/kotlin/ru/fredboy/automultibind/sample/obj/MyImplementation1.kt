package ru.fredboy.automultibind.sample.obj

import ru.fredboy.automultibind.sample.intomap.BindMyInterfaceIntoMap
import ru.fredboy.automultibind.sample.intoset.BindMyInterfaceIntoSet
import javax.inject.Inject

@BindMyInterfaceIntoSet
@BindMyInterfaceIntoMap(stringKey = MyImplementation1.KEY)
class MyImplementation1 @Inject constructor() : IMyInterface {

    override fun getString() = "My implementation #1"

    companion object {
        const val KEY = "MyImplementation1_Key"
    }
}