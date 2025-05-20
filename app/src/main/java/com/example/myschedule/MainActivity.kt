package com.example.myschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.myschedule.data.database.MyScheduleDb
import com.example.myschedule.data.repository.ScheduleRepositoryImpl
import com.example.myschedule.domain.ScheduleRepository
import com.example.myschedule.ui.navigation.NaviGraph
import com.example.myschedule.ui.theme.MyScheduleTheme
import com.example.myschedule.viewmodels.*

class MainActivity : ComponentActivity() {

    private val createScheduleViewModel: CreateScheduleViewModel by viewModels {
        CreateScheduleViewModelFactory(
            ScheduleRepositoryImpl(MyScheduleDb.getDatabase(this).scheduleDao())
        )
    }
    private val modifyScheduleViewModel: ModifyScheduleViewModel by viewModels {
        ModifyScheduleViewModelFactory(
            ScheduleRepositoryImpl(MyScheduleDb.getDatabase(this).scheduleDao())
        )
    }
    private val monthlyScheduleViewModel: MonthlyScheduleViewModel by viewModels {
        MonthlyScheduleViewModelFactory(
            ScheduleRepositoryImpl(MyScheduleDb.getDatabase(this).scheduleDao())
        )
    }
    private lateinit var regionViewModel: RegionViewModel
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            monthlyScheduleViewModel.fetchScheduleList()

            MyScheduleTheme {
                Image(
                    painter = painterResource(R.drawable.background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                val navController = rememberNavController()
                regionViewModel = ViewModelProvider(this)[RegionViewModel::class.java]

                NaviGraph(
                    createScheduleViewModel = createScheduleViewModel,
                    modifyScheduleViewModel = modifyScheduleViewModel,
                    monthlyScheduleViewModel = monthlyScheduleViewModel,
                    regionViewModel = regionViewModel,
                    weatherViewModel = weatherViewModel,
                    navController = navController,
                )
            }
        }
    }
}

class CreateScheduleViewModelFactory(
    private val scheduleRepository: ScheduleRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateScheduleViewModel(scheduleRepository) as T
        }
        throw IllegalArgumentException("CreateScheduleViewModel is unknown class")
    }
}

class ModifyScheduleViewModelFactory(
    private val scheduleRepository: ScheduleRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ModifyScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ModifyScheduleViewModel(scheduleRepository) as T
        }
        throw IllegalArgumentException("ModifyScheduleViewModel is unknown class")
    }
}

class MonthlyScheduleViewModelFactory(
    private val scheduleRepository: ScheduleRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MonthlyScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MonthlyScheduleViewModel(scheduleRepository) as T
        }
        throw IllegalArgumentException("MonthlyScheduleViewModel is unknown class")
    }
}
