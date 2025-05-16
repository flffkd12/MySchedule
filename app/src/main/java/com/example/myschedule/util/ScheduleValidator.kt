package com.example.myschedule.util

import com.example.myschedule.R
import com.example.myschedule.data.Location
import com.example.myschedule.schedulecreate.titletimeinput.TimeCalc

object ScheduleValidator {

    fun validateScheduleInput(
        title: String,
        startTimeAmPm: String,
        startTimeHour: Int,
        startTimeMinute: Int,
        endTimeAmPm: String,
        endTimeHour: Int,
        endTimeMinute: Int,
        location: Location?
    ): Int {
        val scheduleStart = TimeCalc(startTimeAmPm, startTimeHour, startTimeMinute)
        val scheduleEnd = TimeCalc(endTimeAmPm, endTimeHour, endTimeMinute)
        val isScheduleTimeAvailable = scheduleStart < scheduleEnd

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