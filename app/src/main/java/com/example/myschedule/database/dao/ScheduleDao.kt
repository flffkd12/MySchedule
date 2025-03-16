package com.example.myschedule.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.myschedule.database.entity.Schedule
import com.example.myschedule.schedulecreate.titletimeinput.ScheduleTime
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

    @Query("SELECT * FROM schedules")
    suspend fun getAllSchedules(): List<Schedule>

    @Query("UPDATE schedules SET title = :title, startTime = :startTime, endTime = :endTime WHERE id = :id")
    suspend fun modifySchedules(
        id: Long,
        title: String,
        startTime: ScheduleTime,
        endTime: ScheduleTime
    )

    @Query("DELETE FROM schedules WHERE id = :id")
    suspend fun deleteSchedule(id: Long)
}