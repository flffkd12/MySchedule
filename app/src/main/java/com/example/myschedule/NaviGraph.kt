package com.example.myschedule

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myschedule.createschedule.CreateSchedule
import com.example.myschedule.createschedule.titletimeinput.CreateTitleAndTime
import com.example.myschedule.mainscreen.MainScreen
import com.example.myschedule.mainscreen.SelectRegionScreen
import com.example.myschedule.monthlyschedule.ModifySchedule
import com.example.myschedule.monthlyschedule.MonthlySchedule
import com.example.myschedule.viewmodels.*

@Composable
fun NaviGraph(
    createScheduleViewModel: CreateScheduleViewModel,
    modifyScheduleViewModel: ModifyScheduleViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    regionViewModel: RegionViewModel,
    weatherViewModel: WeatherViewModel,
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = Routes.MAIN_SCREEN) {

        composable(route = Routes.MAIN_SCREEN) {
            MainScreen(monthlyScheduleViewModel, weatherViewModel, navController)
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
            MonthlySchedule(modifyScheduleViewModel, monthlyScheduleViewModel, navController)
        }

        composable(route = Routes.MODIFY_SCHEDULE) {
            ModifySchedule(
                modifyScheduleViewModel,
                monthlyScheduleViewModel,
                regionViewModel,
                navController
            )
        }
    }
}
