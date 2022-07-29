package com.hedgehog.simplemvvm.views.current_color

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hedgehog.simplemvvm.R
import com.hedgehog.simplemvvm.model.colors.ColorListener
import com.hedgehog.simplemvvm.model.colors.ColorsRepository
import com.hedgehog.simplemvvm.model.colors.NamedColor
import com.hedgehog.simplemvvm.views.Navigator
import com.hedgehog.simplemvvm.views.UiActions
import com.hedgehog.simplemvvm.views.base.BaseViewModel
import com.hedgehog.simplemvvm.views.change_color.ChangeColorFragment

class CurrentColorViewModel(
    private val navigator: Navigator, private val uiAction: UiActions,
    private val colorsRepository: ColorsRepository
) : BaseViewModel() {

    private val _currentColor = MutableLiveData<NamedColor>()
    val currentColor: LiveData<NamedColor> = _currentColor

    private val colorListener: ColorListener = {
        _currentColor.postValue(it)
    }

    init {
        colorsRepository.addListener(colorListener)
    }

    override fun onCleared() {
        super.onCleared()
        colorsRepository.removeListener(colorListener)
    }

    override fun onResult(result: Any) {
        super.onResult(result)
        if (result is NamedColor) {
            val message = uiAction.getString(R.string.change_color, result.name)
            uiAction.toast(message = message)
        }
        Log.e("pie", "CurrentColorViewModel:onResult: result = $result")
    }

    fun changeColor() {
        val currentColor = currentColor.value ?: return
        val screen = ChangeColorFragment.Screen(currentColorId = currentColor.id)
        navigator.launch(screen = screen)
        Log.e("pie", "changeColor:changeColor")
    }
}