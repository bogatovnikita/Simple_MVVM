package com.hedgehog.simplemvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.hedgehog.simplemvvm.views.HasScreenTitle
import com.hedgehog.simplemvvm.views.base.BaseFragment
import com.hedgehog.simplemvvm.views.current_color.CurrentColorFragment
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val activityViewModel by viewModels<MainViewModel> {
        ViewModelProvider.AndroidViewModelFactory(application = application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            activityViewModel.launchFragment(
                activity = this,
                screen = CurrentColorFragment.Screen(),
                addToBackStack = false
            )
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    override fun onDestroy() {
        Log.e("pie", "MainActivity:onDestroy")
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.e("pie", "onSupportNavigateUp")
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        activityViewModel.whenActivityActive.resource = this
        Log.e("pie", "onResume")
    }

    override fun onPause() {
        super.onPause()
        activityViewModel.whenActivityActive.resource = null
        Log.e("pie", "onPause")
    }

    fun notifyScreenUpdates() {
        val f = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            Log.e("pie", "MainActivity:notifyScreenUpdates: if1")
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            Log.e("pie", "MainActivity:notifyScreenUpdates: else1")

        }
        if (f is HasScreenTitle && f.getScreenTitle() != null) {
            supportActionBar?.title = f.getScreenTitle()
            Log.e("pie", "MainActivity:notifyScreenUpdates: if2")
        } else {
            supportActionBar?.title = getString(R.string.app_name)
            Log.e("pie", "MainActivity:notifyScreenUpdates: else2")
        }

        val result = activityViewModel.result.value?.getValue() ?: return

        if (f is BaseFragment) {
            f.viewModel.onResult(result)
            Log.e("pie", "MainActivity:notifyScreenUpdates: if3")
        }
    }

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            notifyScreenUpdates()
            Log.e("pie", "onFragmentViewCreated")
        }
    }

}