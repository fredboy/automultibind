package ru.fredboy.automultibind.sample.obj

import ru.fredboy.automultibind.sample.intomap.BindMyInterfaceIntoMap
import ru.fredboy.automultibind.sample.intoset.BindMyInterfaceIntoSet
import javax.inject.Inject

@BindMyInterfaceIntoSet
@BindMyInterfaceIntoMap(stringKey = MyImplementation2.KEY)
class MyImplementation2 @Inject constructor() : IMyInterface {

    override fun getString() = "My implementation #2"

    companion object {
        const val KEY = "MyImplementation2_Key"
    }
}