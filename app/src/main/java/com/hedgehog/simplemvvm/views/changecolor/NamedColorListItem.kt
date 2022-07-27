package com.hedgehog.simplemvvm.views.changecolor

import com.hedgehog.simplemvvm.model.colors.NamedColor

data class NamedColorListItem(
    val namedColor: NamedColor,
    val selected: Boolean
)
