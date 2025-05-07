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

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS users")

        database.execSQL("ALTER TABLE schedules RENAME TO schedules_old")

        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS schedules (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                date INTEGER NOT NULL,
                title TEXT NOT NULL,
                regionLocation TEXT NOT NULL,
                startTime TEXT NOT NULL,
                endTime TEXT NOT NULL
            )
        """
        )

        database.execSQL(
            """
            INSERT INTO schedules (id, date, title, regionLocation, startTime, endTime)
            SELECT id, date, title, regionLocation, startTime, endTime
            FROM schedules_old
        """
        )

        database.execSQL("DROP TABLE schedules_old")
    }
}

@Database(entities = [Schedule::class], version = 4)
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
                ).addMigrations(MIGRATION_3_4).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
