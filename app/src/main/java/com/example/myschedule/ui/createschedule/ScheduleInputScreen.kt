package com.example.myschedule.ui.createschedule

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschedule.R
import com.example.myschedule.components.ScheduleTitle
import com.example.myschedule.components.ScrollTimePicker
import com.example.myschedule.components.SelectRegion
import com.example.myschedule.components.bottomnav.BtmNavBar
import com.example.myschedule.data.local.model.RegionLocation
import com.example.myschedule.data.local.model.ScheduleTime
import com.example.myschedule.ui.navigation.Routes
import com.example.myschedule.ui.theme.*
import com.example.myschedule.util.ScheduleValidator
import com.example.myschedule.viewmodels.CreateScheduleViewModel
import com.example.myschedule.viewmodels.MonthlyScheduleViewModel
import com.example.myschedule.viewmodels.RegionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ScheduleInputScreen(
    createScheduleViewModel: CreateScheduleViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    regionViewModel: RegionViewModel,
    navController: NavController,
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
                val title = rememberSaveable { mutableStateOf("") }
                ScheduleTitle(title)

                Text(
                    text = stringResource(R.string.schedule_select_region),
                    style = MaterialTheme.typography.titleMedium
                )

                val selectRegionGuide = stringResource(R.string.select_region)
                val firstRegion = rememberSaveable { mutableStateOf(selectRegionGuide) }
                val secondRegion = rememberSaveable { mutableStateOf(selectRegionGuide) }
                val thirdRegion = rememberSaveable { mutableStateOf(selectRegionGuide) }

                SelectRegion(firstRegion, secondRegion, thirdRegion, regionViewModel)

                Text(
                    text = stringResource(R.string.schedule_start_time),
                    style = MaterialTheme.typography.titleMedium
                )

                val defaultAmString = stringResource(R.string.am)
                val startTimeAmPm = rememberSaveable { mutableStateOf(defaultAmString) }
                val startTimeHour = rememberSaveable { mutableIntStateOf(6) }
                val startTimeMinute = rememberSaveable { mutableIntStateOf(0) }

                ScrollTimePicker(startTimeAmPm, startTimeHour, startTimeMinute)

                Text(
                    text = stringResource(R.string.schedule_end_time),
                    style = MaterialTheme.typography.titleMedium
                )

                val defaultPmString = stringResource(R.string.pm)
                val endTimeAmPm = rememberSaveable { mutableStateOf(defaultPmString) }
                val endTimeHour = rememberSaveable { mutableIntStateOf(6) }
                val endTimeMinute = rememberSaveable { mutableIntStateOf(0) }

                ScrollTimePicker(endTimeAmPm, endTimeHour, endTimeMinute)

                Spacer(modifier = Modifier.weight(1f))

                val startTime = ScheduleTime(
                    startTimeAmPm.value,
                    startTimeHour.intValue,
                    startTimeMinute.intValue,
                )
                val endTime = ScheduleTime(
                    endTimeAmPm.value,
                    endTimeHour.intValue,
                    endTimeMinute.intValue,
                )

                CreateScheduleButton(
                    createScheduleViewModel = createScheduleViewModel,
                    monthlyScheduleViewModel = monthlyScheduleViewModel,
                    regionViewModel = regionViewModel,
                    navController = navController,
                    title = title.value,
                    firstRegion = firstRegion.value,
                    secondRegion = secondRegion.value,
                    thirdRegion = thirdRegion.value,
                    startTime = startTime,
                    endTime = endTime
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
    title: String,
    firstRegion: String,
    secondRegion: String,
    thirdRegion: String,
    startTime: ScheduleTime,
    endTime: ScheduleTime
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    ElevatedButton(
        onClick = {
            val location = regionViewModel.getRegionLocation(firstRegion, secondRegion, thirdRegion)
            val errorResId =
                ScheduleValidator.validateScheduleInput(title, startTime, endTime, location)

            if (errorResId != 0) {
                coroutineScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, errorResId, Toast.LENGTH_SHORT).show()
                }
            } else {
                val regionLocation =
                    RegionLocation(firstRegion, secondRegion, thirdRegion, location!!)

                coroutineScope.launch(Dispatchers.IO) {
                    createScheduleViewModel.saveSchedule(
                        title = title,
                        regionLocation = regionLocation,
                        startTime = startTime,
                        endTime = endTime
                    )
                    monthlyScheduleViewModel.fetchScheduleList()
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
            text = stringResource(R.string.schedule_create_button),
            color = White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
