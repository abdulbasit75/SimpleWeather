package com.simple.weather.common.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.simple.weather.ui.MainActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment(resourceLayout: Int) : Fragment(resourceLayout), HasAndroidInjector,
    BaseView {

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    private val baseActivity: MainActivity? by lazy {
        if (activity is MainActivity) {
            activity as MainActivity
        } else {
            null
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = mDispatchingAndroidInjector

    override fun onAttach(contex: Context) {
        AndroidSupportInjection.inject(this)

        super.onAttach(contex)
    }

    override fun showShortInfo(info: String) {
        baseActivity?.showShortInfo(info)
    }

    override fun showSnackBar(msg: String) {
        baseActivity?.showSnackBar(msg)
    }
}