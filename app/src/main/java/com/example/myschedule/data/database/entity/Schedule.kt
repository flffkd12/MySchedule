package com.example.myschedule.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.myschedule.data.RegionLocation
import com.example.myschedule.schedulecreate.titletimeinput.ScheduleTime
import java.time.LocalDate

@Entity(
    tableName = "schedules",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["email"],
            childColumns = ["userEmail"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Schedule(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userEmail: String,
    val date: LocalDate,
    val title: String,
    val regionLocation: RegionLocation,
    val startTime: ScheduleTime,
    val endTime: ScheduleTime
)