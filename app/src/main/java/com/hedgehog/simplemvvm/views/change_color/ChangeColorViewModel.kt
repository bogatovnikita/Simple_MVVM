package com.hedgehog.simplemvvm.views.change_color

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.hedgehog.simplemvvm.R
import com.hedgehog.simplemvvm.model.colors.ColorsRepository
import com.hedgehog.simplemvvm.model.colors.NamedColor
import com.hedgehog.simplemvvm.views.Navigator
import com.hedgehog.simplemvvm.views.UiActions
import com.hedgehog.simplemvvm.views.base.BaseViewModel

class ChangeColorViewModel(
    screen: ChangeColorFragment.Screen,
    private val navigator: Navigator,
    private val uiActions: UiActions,
    private val colorsRepository: ColorsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(), ColorsAdapter.Listener {

    private val _availableColors = MutableLiveData<List<NamedColor>>()
    private val _currentColorId =
        savedStateHandle.getLiveData("currentColorId", screen.currentColorId)

    private val _colorsList = MediatorLiveData<List<NamedColorListItem>>()
    val colorsList: LiveData<List<NamedColorListItem>> = _colorsList

    private val _screenTitle = MutableLiveData<String>()
    val screenTitle: LiveData<String> = _screenTitle


    init {
        _availableColors.value = colorsRepository.getAvailableColors()
        _colorsList.addSource(_availableColors) { mergeSources() }
        _colorsList.addSource(_currentColorId) { mergeSources() }
    }

    override fun onColorChosen(namedColor: NamedColor) {
        Log.e("pie", "ChangeColorViewModel:onColorChosen")
        _currentColorId.value = namedColor.id
    }

    fun onSavePressed() {
        Log.e("pie", "ChangeColorViewModel:onSavePressed")
        val currentColorId = _currentColorId.value ?: return
        val currentColor = colorsRepository.getById(currentColorId)
        colorsRepository.currentColor = currentColor
        navigator.goBack(result = currentColor)
    }

    fun onCancelPressed() {
        Log.e("pie", "ChangeColorViewModel:onCancelPressed")
        navigator.goBack()
    }

    private fun mergeSources() {
        Log.e("pie", "ChangeColorViewModel:mergeSources")
        val colors = _availableColors.value ?: return
        val currentColorId = _currentColorId.value ?: return
        val currentColor = colors.first { it.id == currentColorId }
        _colorsList.value = colors.map { NamedColorListItem(it, currentColorId == it.id) }
        _screenTitle.value = uiActions.getString(R.string.change_color_screen_title, currentColor.name)
    }
}