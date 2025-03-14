package com.example.myschedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myschedule.login.Login
import com.example.myschedule.login.UserViewModel
import com.example.myschedule.mainscreen.MainScreen
import com.example.myschedule.monthlyschedule.MonthlySchedule
import com.example.myschedule.schedulecreate.CreateSchedule
import com.example.myschedule.schedulecreate.ScheduleViewModel
import com.example.myschedule.schedulecreate.titletimeinput.CreateTitleAndTime

@Composable
fun NaviGraph(
    userViewModel: UserViewModel,
    scheduleViewModel: ScheduleViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val userEmail = userViewModel.userEmail.collectAsState().value
    val userName = userViewModel.userName.collectAsState().value

    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(route = Routes.LOGIN) {
            Login(userViewModel, navController)
        }

        composable(route = Routes.MAIN_SCREEN) {
            MainScreen(navController, userName)
        }

        composable(route = Routes.CREATE_SCHEDULE) {
            CreateSchedule(scheduleViewModel, navController)
        }

        composable(route = Routes.CREATE_TITLE_AND_TIME) {
            CreateTitleAndTime(scheduleViewModel, navController, userEmail)
        }

        composable(route = Routes.MONTHLY_SCHEDULE) {
            MonthlySchedule()
        }
    }
}