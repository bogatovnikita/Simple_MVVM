package com.hedgehog.simplemvvm.views

import com.hedgehog.simplemvvm.views.base.BaseScreen

interface Navigator {

    fun launch(screen: BaseScreen)

    fun goBack(result: Any? = null)
}