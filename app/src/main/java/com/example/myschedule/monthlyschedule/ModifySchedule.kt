package com.example.myschedule.monthlyschedule

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
import com.example.myschedule.RegionViewModel
import com.example.myschedule.Routes
import com.example.myschedule.components.SelectRegion
import com.example.myschedule.data.RegionLocation
import com.example.myschedule.schedulecreate.titletimeinput.ScheduleTime
import com.example.myschedule.schedulecreate.titletimeinput.ScheduleTitle
import com.example.myschedule.schedulecreate.titletimeinput.ScrollTimePicker
import com.example.myschedule.schedulecreate.titletimeinput.TimeCalc
import com.example.myschedule.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ModifySchedule(
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    regionViewModel: RegionViewModel,
    navController: NavController,
    id: Long,
    title: String,
    regionElement: List<String>,
    startTimeElement: List<String>,
    endTimeElement: List<String>
) {

    val focusManager = LocalFocusManager.current

    Scaffold(
        bottomBar = { BtmNavBar(navController, Routes.MONTHLY_SCHEDULE) },
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
                val titleName = rememberSaveable { mutableStateOf(title) }
                ScheduleTitle(titleName)

                Text(
                    text = stringResource(R.string.schedule_select_region),
                    style = MaterialTheme.typography.titleMedium
                )

                val firstRegion = rememberSaveable { mutableStateOf(regionElement[0]) }
                val secondRegion = rememberSaveable { mutableStateOf(regionElement[1]) }
                val thirdRegion = rememberSaveable { mutableStateOf(regionElement[2]) }

                SelectRegion(firstRegion, secondRegion, thirdRegion, regionViewModel)

                Text(
                    text = stringResource(R.string.schedule_start_time),
                    style = MaterialTheme.typography.titleMedium
                )

                val startTimeAmPm = rememberSaveable { mutableStateOf(startTimeElement[0]) }
                val startTimeHour = rememberSaveable { mutableStateOf(startTimeElement[1]) }
                val startTimeMinute = rememberSaveable { mutableStateOf(startTimeElement[2]) }

                ScrollTimePicker(startTimeAmPm, startTimeHour, startTimeMinute)

                Text(
                    text = stringResource(R.string.schedule_end_time),
                    style = MaterialTheme.typography.titleMedium
                )

                val endTimeAmPm = rememberSaveable { mutableStateOf(endTimeElement[0]) }
                val endTimeHour = rememberSaveable { mutableStateOf(endTimeElement[1]) }
                val endTimeMinute = rememberSaveable { mutableStateOf(endTimeElement[2]) }

                ScrollTimePicker(endTimeAmPm, endTimeHour, endTimeMinute)

                Spacer(modifier = Modifier.weight(1f))

                ModifyScheduleButton(
                    monthlyScheduleViewModel = monthlyScheduleViewModel,
                    regionViewModel = regionViewModel,
                    navController = navController,
                    id = id,
                    titleName = titleName.value,
                    firstRegion = firstRegion.value,
                    secondRegion = secondRegion.value,
                    thirdRegion = thirdRegion.value,
                    startTimeAmPm = startTimeAmPm.value,
                    startTimeHour = startTimeHour.value.toInt(),
                    startTimeMinute = startTimeMinute.value.toInt(),
                    endTimeAmPm = endTimeAmPm.value,
                    endTimeHour = endTimeHour.value.toInt(),
                    endTimeMinute = endTimeMinute.value.toInt()
                )
            }
        }
    }
}

@Composable
fun ModifyScheduleButton(
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    regionViewModel: RegionViewModel,
    navController: NavController,
    id: Long,
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
                    monthlyScheduleViewModel.modifySchedule(
                        context,
                        id,
                        titleName,
                        regionLocation,
                        startTime,
                        endTime
                    )

                    monthlyScheduleViewModel.fetchScheduleList(context)
                }

                navController.popBackStack()
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
            text = stringResource(R.string.schedule_modify),
            color = White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
