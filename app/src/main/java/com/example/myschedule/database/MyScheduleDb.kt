package com.example.myschedule.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myschedule.database.dao.ScheduleDao
import com.example.myschedule.database.dao.UserDao
import com.example.myschedule.database.entity.Schedule
import com.example.myschedule.database.entity.User

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE schedules (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "userEmail TEXT NOT NULL, " +
                    "date INTEGER NOT NULL, " +
                    "title TEXT NOT NULL, " +
                    "startTime TEXT NOT NULL, " +
                    "endTime TEXT NOT NULL, " +
                    "FOREIGN KEY(userEmail) REFERENCES users(email) ON DELETE CASCADE" +
                    ")"
        )
    }
}

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
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                instance
            }
        }
    }
}