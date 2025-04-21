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

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE schedules ADD COLUMN regionLocation TEXT NOT NULL DEFAULT ''")
        db.execSQL("DELETE FROM schedules")
    }
}

@Database(entities = [User::class, Schedule::class], version = 3)
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
                ).addMigrations(MIGRATION_2_3).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
