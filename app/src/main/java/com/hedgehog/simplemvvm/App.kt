package com.hedgehog.simplemvvm

import android.app.Application
import com.hedgehog.simplemvvm.model.colors.InMemoryColorsRepository

class App:Application() {

    val models = listOf<Any>(InMemoryColorsRepository())
}