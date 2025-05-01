package com.example.myschedule.schedulecreate.titletimeinput

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschedule.BtmNavBar
import com.example.myschedule.R
import com.example.myschedule.Routes
import com.example.myschedule.components.SelectRegion
import com.example.myschedule.data.RegionLocation
import com.example.myschedule.ui.theme.*
import com.example.myschedule.viewmodels.CreateScheduleViewModel
import com.example.myschedule.viewmodels.MonthlyScheduleViewModel
import com.example.myschedule.viewmodels.RegionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CreateTitleAndTime(
    createScheduleViewModel: CreateScheduleViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    regionViewModel: RegionViewModel,
    navController: NavController,
    userEmail: String?
) {

    val focusManager = LocalFocusManager.current

    Scaffold(
        bottomBar = { BtmNavBar(navController, Routes.CREATE_SCHEDULE) },
        containerColor = LightGreen,
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { focusManager.clearFocus() }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(DefaultAllPadding)
                .clip(RoundedAllCornerShape).background(White)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize().padding(ContentPadding)
            ) {
                val titleName = rememberSaveable { mutableStateOf("") }
                ScheduleTitle(titleName)

                Text(
                    text = stringResource(R.string.schedule_select_region),
                    style = MaterialTheme.typography.titleMedium
                )

                val selectRegionGuide = stringResource(R.string.select_region_guide)
                val firstRegion = rememberSaveable { mutableStateOf(selectRegionGuide) }
                val secondRegion = rememberSaveable { mutableStateOf(selectRegionGuide) }
                val thirdRegion = rememberSaveable { mutableStateOf(selectRegionGuide) }

                SelectRegion(firstRegion, secondRegion, thirdRegion, regionViewModel)

                Text(
                    text = stringResource(R.string.schedule_start_time),
                    style = MaterialTheme.typography.titleMedium
                )

                val startTimeAmPm = rememberSaveable { mutableStateOf("오전") }
                val startTimeHour = rememberSaveable { mutableStateOf("6") }
                val startTimeMinute = rememberSaveable { mutableStateOf("0") }

                ScrollTimePicker(startTimeAmPm, startTimeHour, startTimeMinute)

                Text(
                    text = stringResource(R.string.schedule_end_time),
                    style = MaterialTheme.typography.titleMedium
                )

                val endTimeAmPm = rememberSaveable { mutableStateOf("오후") }
                val endTimeHour = rememberSaveable { mutableStateOf("6") }
                val endTimeMinute = rememberSaveable { mutableStateOf("0") }

                ScrollTimePicker(endTimeAmPm, endTimeHour, endTimeMinute)

                Spacer(modifier = Modifier.weight(1f))

                CreateScheduleButton(
                    createScheduleViewModel = createScheduleViewModel,
                    monthlyScheduleViewModel = monthlyScheduleViewModel,
                    regionViewModel = regionViewModel,
                    navController = navController,
                    userEmail = userEmail!!,
                    titleName = titleName.value,
                    firstRegion = firstRegion.value,
                    secondRegion = secondRegion.value,
                    thirdRegion = thirdRegion.value,
                    startTimeAmPm = startTimeAmPm.value,
                    startTimeHour = startTimeHour.value.toInt(),
                    startTimeMinute = startTimeMinute.value.toInt(),
                    endTimeAmPm = endTimeAmPm.value,
                    endTimeHour = endTimeHour.value.toInt(),
                    endTimeMinute = endTimeHour.value.toInt()
                )
            }
        }
    }
}

@Composable
fun CreateScheduleButton(
    createScheduleViewModel: CreateScheduleViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    regionViewModel: RegionViewModel,
    navController: NavController,
    userEmail: String,
    titleName: String,
    firstRegion: String,
    secondRegion: String,
    thirdRegion: String,
    startTimeAmPm: String,
    startTimeHour: Int,
    startTimeMinute: Int,
    endTimeAmPm: String,
    endTimeHour: Int,
    endTimeMinute: Int,
) {

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    ElevatedButton(
        onClick = {
            val scheduleStart = TimeCalc(startTimeAmPm, startTimeHour, startTimeMinute)
            val scheduleEnd = TimeCalc(endTimeAmPm, endTimeHour, endTimeMinute)
            val isScheduleTimeAvailable = scheduleStart < scheduleEnd
            val location = regionViewModel.getRegionLocation(firstRegion, secondRegion, thirdRegion)

            if (titleName.isEmpty()) {
                coroutineScope.launch(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        R.string.empty_title_message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (titleName.length > 20) {
                coroutineScope.launch(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        R.string.exceeded_title_message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (!isScheduleTimeAvailable) {
                coroutineScope.launch(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        R.string.unavailable_time_message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else if (location == null) {
                coroutineScope.launch(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        R.string.unavailable_region_message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                val regionLocation = RegionLocation(
                    firstRegion = firstRegion,
                    secondRegion = secondRegion,
                    thirdRegion = thirdRegion,
                    location = location
                )
                val startTime = ScheduleTime(
                    amPm = startTimeAmPm,
                    hour = startTimeHour,
                    minute = startTimeMinute
                )
                val endTime = ScheduleTime(
                    amPm = endTimeAmPm,
                    hour = endTimeHour,
                    minute = endTimeMinute
                )

                coroutineScope.launch(Dispatchers.IO) {
                    createScheduleViewModel.saveSchedule(
                        context = context,
                        userEmail = userEmail,
                        title = titleName,
                        regionLocation = regionLocation,
                        startTime = startTime,
                        endTime = endTime
                    )

                    createScheduleViewModel.clearSelectedDate()

                    monthlyScheduleViewModel.fetchScheduleList(context)
                }

                navController.navigate(Routes.MONTHLY_SCHEDULE) {
                    popUpTo(Routes.LOGIN) { inclusive = false }
                }
            }
        },
        shape = RoundedAllCornerShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = LightGreen,
            contentColor = White
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.schedule_create),
            color = White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

fun TimeCalc(amPm: String, hour: Int, minute: Int): Int {
    val minutes = hour % 12 * 60 + minute
    return when (amPm) {
        "오전" -> minutes
        "오후" -> minutes + 12 * 60
        else -> {
            throw IllegalArgumentException("Invalid AM/PM value")
        }
    }
}