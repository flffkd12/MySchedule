package com.example.myschedule.data.database

import androidx.room.TypeConverter
import com.example.myschedule.data.Location
import com.example.myschedule.data.RegionLocation
import com.example.myschedule.data.ScheduleTime
import java.time.LocalDate

class Converters {

    @TypeConverter
    fun fromRegionLocation(regionLocation: RegionLocation): String {
        return "${regionLocation.firstRegion} ${regionLocation.secondRegion} ${regionLocation.thirdRegion} " +
                "${regionLocation.location.x} ${regionLocation.location.y}"
    }

    @TypeConverter
    fun toRegionLocation(regionLocationString: String): RegionLocation {
        val parts = regionLocationString.split(" ")
        return RegionLocation(
            parts[0],
            parts[1],
            parts[2],
            Location(parts[3].toInt(), parts[4].toInt())
        )
    }

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
