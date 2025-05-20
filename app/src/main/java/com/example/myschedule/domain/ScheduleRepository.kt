package com.example.myschedule.domain

import com.example.myschedule.data.RegionLocation
import com.example.myschedule.data.ScheduleTime
import com.example.myschedule.data.database.entity.Schedule

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

}