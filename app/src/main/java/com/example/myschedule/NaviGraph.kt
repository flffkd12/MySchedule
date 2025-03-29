package com.example.myschedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myschedule.loginscreen.LoginScreen
import com.example.myschedule.loginscreen.UserViewModel
import com.example.myschedule.mainscreen.MainScreen
import com.example.myschedule.monthlyschedule.ModifySchedule
import com.example.myschedule.monthlyschedule.MonthlySchedule
import com.example.myschedule.monthlyschedule.MonthlyScheduleViewModel
import com.example.myschedule.schedulecreate.CreateSchedule
import com.example.myschedule.schedulecreate.CreateScheduleViewModel
import com.example.myschedule.schedulecreate.titletimeinput.CreateTitleAndTime

@Composable
fun NaviGraph(
    userViewModel: UserViewModel,
    createScheduleViewModel: CreateScheduleViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(route = Routes.LOGIN) {
            LoginScreen(userViewModel, monthlyScheduleViewModel, navController)
        }

        composable(route = Routes.MAIN_SCREEN) {
            MainScreen(userViewModel, monthlyScheduleViewModel, navController)
        }

        composable(route = Routes.CREATE_SCHEDULE) {
            CreateSchedule(createScheduleViewModel, navController)
        }

        composable(route = Routes.CREATE_TITLE_AND_TIME) {
            val userEmail = userViewModel.userEmail.collectAsState().value
            CreateTitleAndTime(
                createScheduleViewModel,
                monthlyScheduleViewModel,
                navController,
                userEmail
            )
        }

        composable(route = Routes.MONTHLY_SCHEDULE) {
            MonthlySchedule(monthlyScheduleViewModel, navController)
        }

        composable(
            route = Routes.MODIFY_SCHEDULE + "/{id}/{title}/{startTime}/{endTime}",
            arguments = listOf(
                navArgument("id") { type = NavType.LongType },
                navArgument("title") { type = NavType.StringType },
                navArgument("startTime") { type = NavType.StringType },
                navArgument("endTime") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")!!
            val title = backStackEntry.arguments?.getString("title")!!
            val startTimeElement = backStackEntry.arguments?.getString("startTime")!!.split(",")
            val endTimeElement = backStackEntry.arguments?.getString("endTime")!!.split(",")

            ModifySchedule(
                monthlyScheduleViewModel, navController, id, title, startTimeElement, endTimeElement
            )
        }
    }
}
