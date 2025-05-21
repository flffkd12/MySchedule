package com.example.myschedule.data.remote.model

import com.example.myschedule.data.local.model.ScheduleTime
import java.time.LocalDate

data class WeatherDto(
    val date: String,
    val time: String,
    val rainProbability: String,
    val rainCode: Int,
    val rainAmount: String,
    val snowAmount: String,
    val skyCode: Int,
    val temperature: String
)

fun WeatherDto.toWeatherCode(): WeatherCode {
    return when {
        rainCode == 3 -> WeatherCode.SNOWY
        rainCode == 1 || rainCode == 2 || rainCode == 4 -> WeatherCode.RAINY
        rainCode == 0 && skyCode == 4 -> WeatherCode.CLOUDY
        rainCode == 0 && skyCode == 3 -> WeatherCode.SUNNY_WITH_CLOUD
        rainCode == 0 && skyCode == 1 -> WeatherCode.SUNNY
        else -> WeatherCode.ERROR
    }
}

fun WeatherDto.isAfter(endTime: ScheduleTime): Boolean {
    val weatherDate = LocalDate.parse(date, java.time.format.DateTimeFormatter.BASIC_ISO_DATE)
    val weatherHour = time.substring(0, 2).toInt()
    val weatherDateTime = weatherDate.atTime(weatherHour, 0)

    val endHour = if (endTime.amPm == "PM" && endTime.hour != 12) {
        endTime.hour + 12
    } else if (endTime.amPm == "AM" && endTime.hour == 12) {
        0
    } else endTime.hour
    val endDateTime = LocalDate.now().atTime(endHour, 0)

    return weatherDateTime.isAfter(endDateTime)
}
