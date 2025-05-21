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
import androidx.navigation.compose.rememberNavController
import com.example.myschedule.ui.navigation.NaviGraph
import com.example.myschedule.ui.theme.MyScheduleTheme
import com.example.myschedule.viewmodels.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val createScheduleViewModel: CreateScheduleViewModel by viewModels()
    private val modifyScheduleViewModel: ModifyScheduleViewModel by viewModels()
    private val monthlyScheduleViewModel: MonthlyScheduleViewModel by viewModels()
    private val regionViewModel: RegionViewModel by viewModels()
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
