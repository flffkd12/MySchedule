package com.example.myschedule.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeHelper {

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
}
