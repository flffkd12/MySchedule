package com.example.myschedule.util

import com.example.myschedule.data.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object WeatherInfoHelper {

    private val BASE_TIMES = listOf("2300", "0200", "0500", "0800", "1100", "1400", "1700", "2000")
    private const val BASE_TIME_INTERVAL_HOUR = 3

    fun getCurrentBaseDateTime(): Pair<String, String> {
        val currentTime = LocalDateTime.now()
        val validTimesIndex = currentTime.hour / BASE_TIME_INTERVAL_HOUR

        return if (validTimesIndex == 0) {
            Pair(
                currentTime.minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE),
                BASE_TIMES[validTimesIndex]
            )
        } else {
            Pair(currentTime.format(DateTimeFormatter.BASIC_ISO_DATE), BASE_TIMES[validTimesIndex])
        }
    }

    fun getMostDangerousWeatherCode(
        weatherList: List<WeatherDto>,
        endTime: ScheduleTime
    ): WeatherCode {
        if (weatherList.isEmpty()) return WeatherCode.ERROR

        var resultCode = weatherList[0].toWeatherCode()

        for (weather in weatherList) {
            if (weather.isAfter(endTime)) break

            val code = weather.toWeatherCode()
            if (code.dangerLevel > resultCode.dangerLevel) {
                resultCode = code
            }
        }

        return resultCode
    }
}
