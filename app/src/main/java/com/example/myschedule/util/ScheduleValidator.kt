package com.example.myschedule.util

import androidx.compose.ui.res.stringResource
import com.example.myschedule.R
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

private fun TimeCalc(scheduleTime: ScheduleTime): Int {
    val minutes = scheduleTime.hour % 12 * 60 + scheduleTime.minute
    return when (scheduleTime.amPm) {
        stringResource(R.string.am) -> minutes
        stringResource(R.string.pm) -> minutes + 12 * 60
        else -> {
            throw IllegalArgumentException("Invalid AM/PM value")
        }
    }
}
