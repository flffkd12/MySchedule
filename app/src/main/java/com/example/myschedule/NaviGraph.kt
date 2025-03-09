package com.example.myschedule

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myschedule.add_schedule.CreateSchedule
import com.example.myschedule.add_schedule.ScheduleViewModel
import com.example.myschedule.login.Login
import com.example.myschedule.main_screen.MainScreen
import com.example.myschedule.monthly_schedule.MonthlySchedule

@Composable
fun NaviGraph(
    userViewModel: UserViewModel,
    scheduleViewModel: ScheduleViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(route = Routes.LOGIN) {
            Login(userViewModel, navController)
        }

        composable(route = Routes.MAIN_SCREEN) {
            MainScreen(userViewModel, navController)
        }

        composable(route = Routes.CREATE_SCHEDULE) {
            CreateSchedule(scheduleViewModel, navController)
        }

        composable(route = Routes.MONTHLY_SCHEDULE) {
            MonthlySchedule()
        }
    }
}