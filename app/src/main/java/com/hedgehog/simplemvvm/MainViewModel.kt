package com.hedgehog.simplemvvm

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.hedgehog.simplemvvm.utils.Event
import com.hedgehog.simplemvvm.utils.ResourceActions
import com.hedgehog.simplemvvm.views.Navigator
import com.hedgehog.simplemvvm.views.UiActions
import com.hedgehog.simplemvvm.views.base.BaseScreen
import com.hedgehog.simplemvvm.views.base.LiveEvent
import com.hedgehog.simplemvvm.views.base.MutableLiveEvent

const val ARG_SCREEN = "ARG_SCREEN"

class MainViewModel(application: Application) : AndroidViewModel(application), Navigator,
    UiActions {

    val whenActivityActive = ResourceActions<MainActivity>()

    private val _result = MutableLiveEvent<Any>()
    val result: LiveEvent<Any> = _result

    override fun launch(screen: BaseScreen) = whenActivityActive {
        Log.e("pie", "MainViewModel:launch: it=$it")
        launchFragment(it, screen)
    }

    override fun goBack(result: Any?) = whenActivityActive {
        if (result != null) {
            _result.value = Event(result)
            Log.e("pie", "MainViewModel:goBack: Event(value)=${Event(result)}")
        }
        it.onBackPressed()
        Log.e("pie", "MainViewModel:goBack")
    }

    override fun toast(message: String) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
        Log.e("pie", "MainViewModel:toast")
    }

    override fun getString(messageRes: Int, vararg args: Any): String {
        Log.e("pie", "MainViewModel:getString")
        return getApplication<App>().getString(messageRes, *args)
    }

    fun launchFragment(activity: MainActivity, screen: BaseScreen, addToBackStack: Boolean = true) {
        Log.e("pie", "MainViewModel:launchFragment")
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf(ARG_SCREEN to screen)

        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        transaction.setCustomAnimations(
            R.anim.enter,
            R.anim.exit,
            R.anim.pop_enter,
            R.anim.pop_exit
        )
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onCleared() {
        super.onCleared()
        whenActivityActive.clear()
        Log.e("pie", "MainViewModel:onCleared")
    }

}