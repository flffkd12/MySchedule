package com.example.myschedule.data.repository

import com.example.myschedule.data.database.dao.ScheduleDao
import com.example.myschedule.data.database.entity.Schedule
import com.example.myschedule.domain.ScheduleRepository

class ScheduleRepositoryImpl(private val scheduleDao: ScheduleDao) : ScheduleRepository {

    override suspend fun insertSchedule(schedule: Schedule) {
        scheduleDao.insertScheduleIfNotExist(schedule)
    }
}