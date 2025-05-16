package com.example.myschedule.util

import com.example.myschedule.R
import com.example.myschedule.data.Location
import com.example.myschedule.schedulecreate.titletimeinput.TimeCalc

object ScheduleValidator {
    fun validateScheduleInput(
        title: String,
        startTimeAmPm: String,
        startTimeHour: String,
        startTimeMinute: String,
        endTimeAmPm: String,
        endTimeHour: String,
        endTimeMinute: String,
        location: Location?
    ): Int {
        val scheduleStart = TimeCalc(startTimeAmPm, startTimeHour.toInt(), startTimeMinute.toInt())
        val scheduleEnd = TimeCalc(endTimeAmPm, endTimeHour.toInt(), endTimeMinute.toInt())
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