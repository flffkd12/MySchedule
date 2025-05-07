package com.example.myschedule

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myschedule.mainscreen.MainScreen
import com.example.myschedule.mainscreen.SelectRegionScreen
import com.example.myschedule.monthlyschedule.ModifySchedule
import com.example.myschedule.monthlyschedule.MonthlySchedule
import com.example.myschedule.schedulecreate.CreateSchedule
import com.example.myschedule.schedulecreate.titletimeinput.CreateTitleAndTime
import com.example.myschedule.viewmodels.*

@Composable
fun NaviGraph(
    userViewModel: UserViewModel,
    createScheduleViewModel: CreateScheduleViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    regionViewModel: RegionViewModel,
    weatherViewModel: WeatherViewModel,
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Routes.MAIN_SCREEN) {

        composable(route = Routes.MAIN_SCREEN) {
            MainScreen(userViewModel, monthlyScheduleViewModel, weatherViewModel, navController)
        }

        composable(route = Routes.SELECT_REGION_SCREEN) {
            SelectRegionScreen(regionViewModel, weatherViewModel, navController)
        }

        composable(route = Routes.CREATE_SCHEDULE) {
            CreateSchedule(createScheduleViewModel, navController)
        }

        composable(route = Routes.CREATE_TITLE_AND_TIME) {
            CreateTitleAndTime(
                createScheduleViewModel,
                monthlyScheduleViewModel,
                regionViewModel,
                navController
            )
        }

        composable(route = Routes.MONTHLY_SCHEDULE) {
            MonthlySchedule(monthlyScheduleViewModel, navController)
        }

        composable(
            route = Routes.MODIFY_SCHEDULE + "/{id}/{title}/{region}/{startTime}/{endTime}",
            arguments = listOf(
                navArgument("id") { type = NavType.LongType },
                navArgument("title") { type = NavType.StringType },
                navArgument("region") { type = NavType.StringType },
                navArgument("startTime") { type = NavType.StringType },
                navArgument("endTime") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")!!
            val title = backStackEntry.arguments?.getString("title")!!
            val regionElement = backStackEntry.arguments?.getString("region")!!.split(",")
            val startTimeElement = backStackEntry.arguments?.getString("startTime")!!.split(",")
            val endTimeElement = backStackEntry.arguments?.getString("endTime")!!.split(",")

            ModifySchedule(
                monthlyScheduleViewModel,
                regionViewModel,
                navController,
                id,
                title,
                regionElement,
                startTimeElement,
                endTimeElement
            )
        }
    }
}
