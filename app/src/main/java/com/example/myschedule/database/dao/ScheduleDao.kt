package com.example.myschedule.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.myschedule.schedulecreate.titletimeinput.ScheduleTime
import com.example.myschedule.database.entity.Schedule
import java.time.LocalDate

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedules WHERE date = :date AND title = :title AND startTime = :startTime AND endTime=:endTime")
    suspend fun findSameSchedule(
        date: LocalDate,
        title: String,
        startTime: ScheduleTime,
        endTime: ScheduleTime,
    ): Schedule?

    @Insert
    suspend fun insertSchedule(schedule: Schedule)

    @Transaction
    suspend fun insertScheduleIfNotExist(schedule: Schedule) {
        val existingSchedule = findSameSchedule(
            schedule.date,
            schedule.title,
            schedule.startTime,
            schedule.endTime
        )

        if (existingSchedule == null) {
            insertSchedule(schedule)
        }
    }
}