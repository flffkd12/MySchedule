package com.example.myschedule.util

import com.example.myschedule.data.WeatherCode
import com.example.myschedule.data.WeatherDto
import com.example.myschedule.data.isBeforeOrEqualTo
import com.example.myschedule.data.toWeatherCode
import com.example.myschedule.schedulecreate.titletimeinput.ScheduleTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object WeatherInfoHelper {

    fun getCurrentBaseDateTime(): Pair<String, String> {
        val currentTime = LocalDateTime.now()
        val validTimes = listOf("2300", "0200", "0500", "0800", "1100", "1400", "1700", "2000")
        val validTimesIndex = currentTime.hour / 3

        return if (validTimesIndex == 0) {
            Pair(
                currentTime.minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE),
                validTimes[validTimesIndex]
            )
        } else {
            Pair(currentTime.format(DateTimeFormatter.BASIC_ISO_DATE), validTimes[validTimesIndex])
        }
    }

    fun getMostDangerousWeatherCode(
        weatherList: List<WeatherDto>,
        endTime: ScheduleTime
    ): WeatherCode {
        if (weatherList.isEmpty()) return WeatherCode.ERROR

        var resultCode = weatherList[0].toWeatherCode()

        for (weather in weatherList) {
            if (!weather.isBeforeOrEqualTo(endTime)) break

            val code = weather.toWeatherCode()
            if (code.dangerLevel > resultCode.dangerLevel) {
                resultCode = code
            }
        }

        return resultCode
    }
}
