package com.example.myschedule.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myschedule.data.database.dao.ScheduleDao
import com.example.myschedule.data.database.entity.Schedule

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("UPDATE schedules SET startTime = REPLACE(startTime, ',', ' ')")
        database.execSQL("UPDATE schedules SET endTime = REPLACE(endTime, ',', ' ')")
    }
}

@Database(entities = [Schedule::class], version = 5)
@TypeConverters(Converters::class)
abstract class MyScheduleDb : RoomDatabase() {

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
                ).addMigrations(MIGRATION_4_5).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
