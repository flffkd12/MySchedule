package com.example.myschedule.domain

import com.example.myschedule.data.local.database.entity.Schedule
import com.example.myschedule.data.local.model.RegionLocation
import com.example.myschedule.data.local.model.ScheduleTime

interface ScheduleRepository {

    suspend fun insertSchedules(schedule: List<Schedule>)

    suspend fun getAllSchedules(): List<Schedule>

    suspend fun modifySchedule(
        id: Long,
        title: String,
        regionLocation: RegionLocation,
        startTime: ScheduleTime,
        endTime: ScheduleTime
    )

    suspend fun deleteSchedule(id: Long)
}