package com.hedgehog.simplemvvm.views.change_color

import com.hedgehog.simplemvvm.model.colors.NamedColor

data class NamedColorListItem(
    val namedColor: NamedColor,
    val selected: Boolean
)
