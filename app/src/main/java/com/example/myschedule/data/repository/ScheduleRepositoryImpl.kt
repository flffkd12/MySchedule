package com.example.myschedule.data.repository

import com.example.myschedule.data.RegionLocation
import com.example.myschedule.data.ScheduleTime
import com.example.myschedule.data.database.dao.ScheduleDao
import com.example.myschedule.data.database.entity.Schedule
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
