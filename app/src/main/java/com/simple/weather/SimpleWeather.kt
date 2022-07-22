package com.simple.weather

import android.app.Application
import com.simple.weather.di.components.ApplicationComponent
import com.simple.weather.di.components.DaggerApplicationComponent
import com.simple.weather.di.modules.ContextModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class SimpleWeather : Application(), HasAndroidInjector {

    @Inject
    lateinit var mDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    lateinit var mComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        setupDI()
    }

    private fun setupDI() {
        DaggerApplicationComponent.builder()
            .contextModule(ContextModule(this))
            .build()
            .also { mComponent = it }
            .inject(this)
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    override fun androidInjector(): AndroidInjector<Any> {
        return mDispatchingAndroidInjector
    }
}

