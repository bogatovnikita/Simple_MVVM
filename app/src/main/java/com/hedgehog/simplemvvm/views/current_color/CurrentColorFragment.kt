package com.hedgehog.simplemvvm.views.current_color

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hedgehog.simplemvvm.databinding.FragmentCurrentColorBinding
import com.hedgehog.simplemvvm.views.base.BaseFragment
import com.hedgehog.simplemvvm.views.base.BaseScreen
import com.hedgehog.simplemvvm.views.base.BaseViewModel
import com.hedgehog.simplemvvm.views.base.screenViewModel

class CurrentColorFragment : BaseFragment() {

    class Screen : BaseScreen

    override val viewModel by screenViewModel<CurrentColorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCurrentColorBinding.inflate(inflater, container, false)
        viewModel.currentColor.observe(viewLifecycleOwner) {
            binding.colorView.setBackgroundColor(it.value)
        }
        binding.changeColorButton.setOnClickListener {
            viewModel.changeColor()
        }
        return binding.root
    }
}