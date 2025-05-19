package com.example.myschedule.util

import com.example.myschedule.R
import com.example.myschedule.createschedule.titletimeinput.TimeCalc
import com.example.myschedule.data.Location
import com.example.myschedule.data.ScheduleTime

object ScheduleValidator {

    fun validateScheduleInput(
        title: String,
        startTime: ScheduleTime,
        endTime: ScheduleTime,
        location: Location?
    ): Int {
        val isScheduleTimeAvailable = TimeCalc(startTime) < TimeCalc(endTime)
        val errorResId = if (title.isEmpty()) {
            R.string.empty_title_message
        } else if (title.length > 20) {
            R.string.exceeded_title_message
        } else if (!isScheduleTimeAvailable) {
            R.string.unavailable_time_message
        } else if (location == null) {
            R.string.unavailable_region_message
        } else {
            0
        }

        return errorResId
    }
}