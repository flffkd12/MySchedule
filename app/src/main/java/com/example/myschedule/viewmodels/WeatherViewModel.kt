package com.example.myschedule.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myschedule.data.Location
import com.example.myschedule.data.RetrofitInstance
import com.example.myschedule.data.WeatherCode
import com.example.myschedule.schedulecreate.titletimeinput.ScheduleTime
import com.example.myschedule.util.WeatherInfoHelper

class WeatherViewModel : ViewModel() {

    suspend fun getWeatherInfo(endTime: ScheduleTime, location: Location): WeatherCode {
        val (baseDate, baseTime) = WeatherInfoHelper.getCurrentBaseDateTime()
        val weatherList = RetrofitInstance.weatherApi.getWeatherInfoList(
            baseDate, baseTime, location.x.toString(), location.y.toString()
        )

        return WeatherInfoHelper.getMostDangerousWeatherCode(weatherList.data, endTime)
    }
}
