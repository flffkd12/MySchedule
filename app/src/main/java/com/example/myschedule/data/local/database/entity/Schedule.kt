package com.example.myschedule.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myschedule.data.local.model.RegionLocation
import com.example.myschedule.data.local.model.ScheduleTime
import java.time.LocalDate

@Entity(tableName = "schedules")
data class Schedule(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: LocalDate,
    val title: String,
    val regionLocation: RegionLocation,
    val startTime: ScheduleTime,
    val endTime: ScheduleTime
)