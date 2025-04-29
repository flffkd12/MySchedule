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
        val validTimes = listOf("0200", "0500", "0800", "1100", "1400", "1700", "2000", "2300")

        // 현재 시간 문자열 생성
        val currentHourStr = currentTime.format(DateTimeFormatter.ofPattern("HH00"))
        var closestIndex = validTimes.indexOfLast { it <= currentHourStr }

        // 현재 시간이 0~2시인 경우
        if (closestIndex == -1) {
            return Pair(
                currentTime.minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE),
                validTimes.last()
            )
        }

        // 현재 시각이 validTimes 정각 ~ 정각 + 10분 인지 확인
        val baseTime = currentTime.withHour(3 * closestIndex + 2)
        if (currentTime.isAfter(baseTime) && currentTime.isBefore(baseTime.plusMinutes(10))) {
            closestIndex -= 1
            if (closestIndex == -1) {
                return Pair(
                    currentTime.minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE),
                    validTimes.last()
                )
            }
        }

        return Pair(
            currentTime.format(DateTimeFormatter.BASIC_ISO_DATE),
            validTimes[closestIndex]
        )
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
