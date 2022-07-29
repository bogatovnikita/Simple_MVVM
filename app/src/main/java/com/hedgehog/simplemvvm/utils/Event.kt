package com.hedgehog.simplemvvm.utils

import android.util.Log

class Event<T>(private val value: T) {
    private var handled: Boolean = false

    fun getValue(): T? {
        Log.e("pie", "Event:getValue:value = $value")
        if (handled) return null
        handled = true
        return value
    }
}