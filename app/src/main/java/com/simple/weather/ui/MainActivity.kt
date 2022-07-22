package com.simple.weather.ui

import android.os.Bundle
import com.simple.weather.R
import com.simple.weather.common.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}