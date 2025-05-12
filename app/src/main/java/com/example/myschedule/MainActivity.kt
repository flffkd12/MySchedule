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
import com.example.myschedule.data.database.MyScheduleDb
import com.example.myschedule.ui.theme.MyScheduleTheme
import com.example.myschedule.viewmodels.CreateScheduleViewModel
import com.example.myschedule.viewmodels.MonthlyScheduleViewModel
import com.example.myschedule.viewmodels.RegionViewModel
import com.example.myschedule.viewmodels.WeatherViewModel

class MainActivity : ComponentActivity() {

    private val createScheduleViewModel: CreateScheduleViewModel by viewModels()
    private val monthlyScheduleViewModel: MonthlyScheduleViewModel by viewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var regionViewModel: RegionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current

            MyScheduleDb.getDatabase(context)
            monthlyScheduleViewModel.fetchScheduleList(context)

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
                    monthlyScheduleViewModel = monthlyScheduleViewModel,
                    regionViewModel = regionViewModel,
                    weatherViewModel = weatherViewModel,
                    navController = navController,
                )
            }
        }
    }
}
