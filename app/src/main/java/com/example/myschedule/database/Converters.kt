package com.example.myschedule.database

import androidx.room.TypeConverter
import com.example.myschedule.add_schedule.ScheduleTime
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate): Long {
        return date.toEpochDay()
    }

    @TypeConverter
    fun toLocalDate(epochDay: Long): LocalDate {
        return epochDay.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun fromScheduleTime(time: ScheduleTime): String {
        return "${time.amPm},${time.hour},${time.minute}"
    }

    @TypeConverter
    fun toScheduleTime(timeString: String): ScheduleTime {
        val parts = timeString.split(",")
        return ScheduleTime(parts[0], parts[1].toInt(), parts[2].toInt())
    }
}