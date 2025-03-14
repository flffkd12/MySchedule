package com.example.myschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import com.example.myschedule.login.UserViewModel
import com.example.myschedule.monthlyschedule.MonthlyScheduleViewModel
import com.example.myschedule.schedulecreate.CreateScheduleViewModel
import com.example.myschedule.ui.theme.MyScheduleTheme

class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels { UserViewModel.Factory }
    private val createScheduleViewModel: CreateScheduleViewModel by viewModels()
    private val monthlyScheduleViewModel: MonthlyScheduleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyScheduleTheme {
                Image(
                    painter = painterResource(R.drawable.background),
                    contentDescription = "background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Scaffold(
                    containerColor = Color.Transparent,
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NaviGraph(
                        userViewModel = userViewModel,
                        createScheduleViewModel = createScheduleViewModel,
                        monthlyScheduleViewModel = monthlyScheduleViewModel,
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}