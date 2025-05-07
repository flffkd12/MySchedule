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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.myschedule.ui.theme.MyScheduleTheme
import com.example.myschedule.viewmodels.*

class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels { UserViewModel.Factory }
    private val createScheduleViewModel: CreateScheduleViewModel by viewModels()
    private val monthlyScheduleViewModel: MonthlyScheduleViewModel by viewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var regionViewModel: RegionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            monthlyScheduleViewModel.fetchScheduleList(LocalContext.current)
            
            MyScheduleTheme {
                regionViewModel = ViewModelProvider(this)[RegionViewModel::class.java]

                Image(
                    painter = painterResource(R.drawable.background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                val navController = rememberNavController()
                NaviGraph(
                    userViewModel = userViewModel,
                    createScheduleViewModel = createScheduleViewModel,
                    monthlyScheduleViewModel = monthlyScheduleViewModel,
                    regionViewModel = regionViewModel,
                    weatherViewModel = weatherViewModel,
                    navController = navController,
                )
            }
        }
    }
}
