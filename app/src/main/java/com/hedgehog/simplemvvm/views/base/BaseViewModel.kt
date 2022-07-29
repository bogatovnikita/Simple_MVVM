package com.hedgehog.simplemvvm.views.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hedgehog.simplemvvm.utils.Event

typealias LiveEvent<T> = LiveData<Event<T>>
typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>

open class BaseViewModel : ViewModel() {
    open fun onResult(result: Any) {
        Log.e("pie", "BaseViewModel:onResult")
    }
}