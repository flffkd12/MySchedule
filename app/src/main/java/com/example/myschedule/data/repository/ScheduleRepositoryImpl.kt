package com.example.myschedule.data.repository

import com.example.myschedule.data.local.database.dao.ScheduleDao
import com.example.myschedule.data.local.database.entity.Schedule
import com.example.myschedule.data.local.model.RegionLocation
import com.example.myschedule.data.local.model.ScheduleTime
import com.example.myschedule.domain.ScheduleRepository

class ScheduleRepositoryImpl(private val scheduleDao: ScheduleDao) : ScheduleRepository {

    override suspend fun insertSchedules(schedule: List<Schedule>) {
        scheduleDao.insertSchedules(schedule)
    }

    override suspend fun getAllSchedules(): List<Schedule> {
        return scheduleDao.getAllSchedules()
    }

    override suspend fun modifySchedule(
        id: Long,
        title: String,
        regionLocation: RegionLocation,
        startTime: ScheduleTime,
        endTime: ScheduleTime
    ) {
        scheduleDao.modifySchedule(id, title, regionLocation, startTime, endTime)
    }

    override suspend fun deleteSchedule(id: Long) {
        scheduleDao.deleteSchedule(id)
    }
}
