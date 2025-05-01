package com.example.myschedule.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myschedule.data.Location
import com.example.myschedule.data.RetrofitInstance
import com.example.myschedule.data.WeatherCode
import com.example.myschedule.schedulecreate.titletimeinput.ScheduleTime
import com.example.myschedule.util.WeatherInfoHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WeatherViewModel : ViewModel() {

    private val _regionName = MutableStateFlow("날씨를 확인할 지역 선택")
    val regionName: StateFlow<String> = _regionName.asStateFlow()

    suspend fun getWeatherInfo(endTime: ScheduleTime, location: Location): WeatherCode {
        val (baseDate, baseTime) = WeatherInfoHelper.getCurrentBaseDateTime()
        val weatherList = RetrofitInstance.weatherApi.getWeatherInfoList(
            baseDate, baseTime, location.x.toString(), location.y.toString()
        )

        return WeatherInfoHelper.getMostDangerousWeatherCode(weatherList.data, endTime)
    }

    fun setRegionName(regionName: String) {
        _regionName.value = regionName
    }
}
