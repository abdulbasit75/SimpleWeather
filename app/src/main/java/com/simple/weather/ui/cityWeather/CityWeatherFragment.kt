package com.simple.weather.ui.cityWeather

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simple.weather.R
import com.simple.weather.common.base.BaseFragment
import com.simple.weather.common.base.baseModels.ResourceStateObserver
import com.simple.weather.common.viewmodel.SharedViewModel
import com.simple.weather.databinding.FragmentCityWeatherBinding
import com.simple.weather.remote.model.City
import com.simple.weather.remote.model.response.nextDays.DaysForecast
import com.simple.weather.remote.model.response.nextHours.HoursForecast
import com.simple.weather.ui.cityWeather.adapter.DaysAdapter
import com.simple.weather.ui.cityWeather.adapter.HoursAdapter
import com.simple.weather.utils.viewBinding

class CityWeatherFragment : BaseFragment(R.layout.fragment_city_weather), MenuProvider {
    private var mCity: City? = null

    private lateinit var mDaysAdapter: DaysAdapter
    private lateinit var mHoursAdapter: HoursAdapter

    private val mBinding by viewBinding(FragmentCityWeatherBinding::bind)
    private val mViewModel: SharedViewModel by viewModels { mViewModelFactory }

    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mCity = arguments?.getParcelable(CITY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObjects()
        setupUI()
        initObservers()
    }

    private fun initObservers() {
        mViewModel.nextHoursForecast.observe(viewLifecycleOwner, ResourceStateObserver(this,
            onSuccess = { it?.let { onGettingNextHoursForecast(it) } },
            onError = { onGettingNextHoursForecastError(it) }))

        mViewModel.nextDaysForecast.observe(viewLifecycleOwner, ResourceStateObserver(this,
            onSuccess = { it?.let { onGettingNextDaysForecast(it) } },
            onError = { onGettingNextDaysForecastError(it) }))
    }

    private fun setupUI() {
        setupHoursRecyclerView()
        setupDaysRecyclerView()
    }

    private fun initObjects() {
        getWeatherForecast()

        mHoursAdapter = HoursAdapter(requireContext(), arrayListOf())
        mDaysAdapter = DaysAdapter(requireContext(), arrayListOf())
    }

    private fun setupHoursRecyclerView() {
        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.HORIZONTAL)
        mBinding.nextHoursRv.addItemDecoration(divider)

        mBinding.nextHoursRv.adapter = mHoursAdapter
    }

    private fun setupDaysRecyclerView() {
        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        mBinding.nextDaysRv.addItemDecoration(divider)

        mBinding.nextDaysRv.adapter = mDaysAdapter
    }

    private fun getWeatherForecast() {
        mViewModel.getNextHoursWeather(mCity?.latitude, mCity?.longitude,
            getString(R.string.unit_metric))

        mViewModel.getNextDaysWeather(40, mCity?.latitude, mCity?.longitude,
            getString(R.string.unit_metric))
    }

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////

    private fun onGettingNextHoursForecast(objHoursForecast: HoursForecast) {
        if (objHoursForecast.hourlyList != null && objHoursForecast.hourlyList.isNotEmpty()) {
            mHoursAdapter.updateList(objHoursForecast.hourlyList)

            handleStatus(true, mBinding.nextHoursRv, mBinding.noHoursTv, mBinding.hoursPb,
                null)
        } else {
            handleStatus(true, mBinding.nextHoursRv, mBinding.noHoursTv, mBinding.hoursPb,
                getString(R.string.no_weather_forecast_not_found))
        }

        mBinding.hoursPb.hide()
    }

    private fun onGettingNextHoursForecastError(errorMsg: String) {
        handleStatus(false, mBinding.nextHoursRv, mBinding.noHoursTv, mBinding.hoursPb,
            errorMsg)
    }

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////

    private fun onGettingNextDaysForecast(objDaysForecast: DaysForecast) {
        if (objDaysForecast.nextFiveDays?.isNotEmpty() == true) {
            mDaysAdapter.updateList(objDaysForecast.nextFiveDays?.toList()!!)

            handleStatus(true, mBinding.nextDaysRv, mBinding.noDaysTv, mBinding.daysPb,
                null)
        } else {
            handleStatus(true, mBinding.nextDaysRv, mBinding.noDaysTv, mBinding.daysPb,
                getString(R.string.no_weather_forecast_not_found))
        }

        mBinding.daysPb.hide()
    }


    private fun onGettingNextDaysForecastError(errorMsg: String) {
        handleStatus(false, mBinding.nextDaysRv, mBinding.noDaysTv, mBinding.daysPb,
            errorMsg)
    }

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.city_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_refresh -> onClickRefresh()
        }

        return false
    }

    private fun onClickRefresh() {
        getWeatherForecast()

        mBinding.apply {
            noDaysTv.visibility = View.GONE
            daysPb.visibility = View.VISIBLE
            nextDaysRv.visibility = View.GONE

            noHoursTv.visibility = View.GONE
            hoursPb.visibility = View.VISIBLE
            nextHoursRv.visibility = View.GONE
        }

    }

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////

    private fun handleStatus(success: Boolean, recyclerView: RecyclerView, errorTv: TextView,
                             loader: ContentLoadingProgressBar, errorMsg: String? = null) {
        loader.hide()

        if (success && errorMsg == null) {
            errorTv.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        } else if (success && errorMsg != null) {
            recyclerView.visibility = View.GONE
            errorTv.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.GONE
            errorTv.apply {
                visibility = View.VISIBLE
                text = errorMsg
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////

    companion object {
        private const val CITY = "City"

        fun newInstance(objCity: City?): CityWeatherFragment {
            return CityWeatherFragment().apply {
                arguments = bundleOf(CITY to objCity)
            }
        }
    }
}