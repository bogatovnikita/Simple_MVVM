package com.hedgehog.simplemvvm.views.base

import androidx.fragment.app.Fragment
import com.hedgehog.simplemvvm.MainActivity

abstract class BaseFragment : Fragment() {
    abstract val viewModel: BaseViewModel

    fun notifyScreenUpdates() {
        (requireActivity() as MainActivity).notifyScreenUpdates()
    }

}