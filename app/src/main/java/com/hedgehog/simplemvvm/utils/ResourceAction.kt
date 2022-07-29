package com.hedgehog.simplemvvm.utils

import android.util.Log

typealias ResourceAction<T> = (T) -> Unit

class ResourceActions<T> {

    var resource: T? = null
        set(newValue) {
            Log.e("pie", "ResourceActions:resource newValue=$newValue//field = $field")
            field = newValue
            if (newValue != null) {
                actions.forEach { it(newValue) }
                actions.clear()
            }
        }
    private val actions = mutableListOf<ResourceAction<T>>()

    operator fun invoke(action: ResourceAction<T>) {
        Log.e("pie", "ResourceActions:invoke:action=$action")
        val resource = this.resource
        if (resource == null) {
            actions += action
        } else {
            action(resource)
        }
    }

    fun clear() {
        actions.clear()
    }
}