package com.example.myschedule.viewmodels

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.myschedule.R
import com.example.myschedule.data.Location
import com.example.myschedule.data.RetrofitInstance
import com.example.myschedule.data.WeatherCode
import com.example.myschedule.data.WeatherDto
import com.example.myschedule.schedulecreate.titletimeinput.ScheduleTime
import com.example.myschedule.util.WeatherInfoHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WeatherViewModel : ViewModel() {

    private val _regionName = MutableStateFlow(stringResource(R.string.select_region_guide))
    val regionName: StateFlow<String> = _regionName.asStateFlow()

    private val _regionLocation = MutableStateFlow<Location?>(null)
    val regionLocation: StateFlow<Location?> = _regionLocation.asStateFlow()

    suspend fun getScheduleWeatherInfo(endTime: ScheduleTime, location: Location): WeatherCode {
        val (baseDate, baseTime) = WeatherInfoHelper.getCurrentBaseDateTime()
        val weatherList = RetrofitInstance.weatherApi.getWeatherInfoList(
            baseDate, baseTime, location.x.toString(), location.y.toString()
        )

        return WeatherInfoHelper.getMostDangerousWeatherCode(weatherList.data, endTime)
    }

    suspend fun getRegionWeatherInfo(location: Location): List<WeatherDto> {
        val (baseDate, baseTime) = WeatherInfoHelper.getCurrentBaseDateTime()
        return RetrofitInstance.weatherApi.getWeatherInfoList(
            baseDate, baseTime, location.x.toString(), location.y.toString()
        ).data
    }

    fun setRegionName(regionName: String) {
        _regionName.value = regionName
    }

    fun setRegionLocation(location: Location) {
        _regionLocation.value = location
    }
}
