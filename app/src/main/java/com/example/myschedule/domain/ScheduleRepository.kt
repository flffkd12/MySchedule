package com.example.myschedule.domain

import com.example.myschedule.data.database.entity.Schedule

interface ScheduleRepository {

    suspend fun insertSchedule(schedule: Schedule)
}