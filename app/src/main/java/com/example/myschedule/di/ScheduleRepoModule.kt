package com.example.myschedule.di

import android.content.Context
import com.example.myschedule.data.database.MyScheduleDb
import com.example.myschedule.data.database.dao.ScheduleDao
import com.example.myschedule.data.repository.ScheduleRepositoryImpl
import com.example.myschedule.domain.ScheduleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScheduleRepoModule {

    @Provides
    @Singleton
    fun provideScheduleDb(@ApplicationContext context: Context): MyScheduleDb {
        return MyScheduleDb.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideScheduleDao(myScheduleDb: MyScheduleDb): ScheduleDao {
        return myScheduleDb.scheduleDao()
    }

    @Provides
    @Singleton
    fun provideScheduleRepository(scheduleDao: ScheduleDao): ScheduleRepository {
        return ScheduleRepositoryImpl(scheduleDao)
    }
}
