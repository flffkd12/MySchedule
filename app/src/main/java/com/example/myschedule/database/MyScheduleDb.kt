package com.example.myschedule.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myschedule.database.dao.ScheduleDao
import com.example.myschedule.database.dao.UserDao
import com.example.myschedule.database.entity.Schedule
import com.example.myschedule.database.entity.User

@Database(entities = [User::class, Schedule::class], version = 2)
@TypeConverters(Converters::class)
abstract class MyScheduleDb : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        @Volatile
        private var INSTANCE: MyScheduleDb? = null

        fun getDatabase(context: Context): MyScheduleDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyScheduleDb::class.java,
                    "my_schedule_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
