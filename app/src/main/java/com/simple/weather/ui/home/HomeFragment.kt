package com.simple.weather.ui.home

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.simple.weather.R
import com.simple.weather.common.base.BaseFragment
import com.simple.weather.databinding.FragmentHomeBinding
import com.simple.weather.ui.home.adapter.WeatherPagerAdapter
import com.simple.weather.utils.cities
import com.simple.weather.utils.viewBinding

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val mBinding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        val adapter = WeatherPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        mBinding.viewPager.adapter = adapter

        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
            tab.text = cities[position].name
        }.attach()
    }
}