package com.hedgehog.simplemvvm.views.base

import android.util.Log
import androidx.fragment.app.Fragment
import com.hedgehog.simplemvvm.MainActivity

abstract class BaseFragment : Fragment() {
    abstract val viewModel: BaseViewModel

    fun notifyScreenUpdates() {
        Log.e("pie", "BaseFragment:notifyScreenUpdates")
        (requireActivity() as MainActivity).notifyScreenUpdates()
    }

}