package com.simple.weather.common.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasAndroidInjector, BaseView {

    @Inject
    lateinit var mDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = mDispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)
    }

    override fun showShortInfo(info: String) {
        Toast.makeText(baseContext, info, Toast.LENGTH_LONG).show()
    }

    override fun showSnackBar(msg: String) {
        Snackbar.make(this.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }
}