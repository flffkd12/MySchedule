package com.example.myschedule.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myschedule.data.Location
import com.example.myschedule.data.RetrofitInstance
import com.example.myschedule.util.DateTimeHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    fun getWeatherInfo(
        location: Location
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val (baseDate, baseTime) = DateTimeHelper.getCurrentBaseDateTime()
            val weatherList = RetrofitInstance.weatherApi.getWeatherInfoList(
                baseDate, baseTime, location.x.toString(), location.y.toString()
            )
        }
    }
}
