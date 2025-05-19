package com.example.myschedule.monthlyschedule

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.myschedule.components.ScheduleTitle
import com.example.myschedule.components.ScrollTimePicker
import com.example.myschedule.components.SelectRegion
import com.example.myschedule.data.ScheduleTime
import com.example.myschedule.ui.theme.*
import com.example.myschedule.util.ScheduleValidator
import com.example.myschedule.viewmodels.ModifyScheduleViewModel
import com.example.myschedule.viewmodels.MonthlyScheduleViewModel
import com.example.myschedule.viewmodels.RegionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ModifySchedule(
    modifyScheduleViewModel: ModifyScheduleViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    regionViewModel: RegionViewModel,
    navController: NavController
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
                ScheduleTitle(title = modifyScheduleViewModel.title)

                Text(
                    text = stringResource(R.string.schedule_select_region),
                    style = MaterialTheme.typography.titleMedium
                )

                SelectRegion(
                    firstRegion = modifyScheduleViewModel.firstRegion,
                    secondRegion = modifyScheduleViewModel.secondRegion,
                    thirdRegion = modifyScheduleViewModel.thirdRegion,
                    regionViewModel = regionViewModel
                )

                Text(
                    text = stringResource(R.string.schedule_start_time),
                    style = MaterialTheme.typography.titleMedium
                )

                ScrollTimePicker(
                    selectedAmPm = modifyScheduleViewModel.startTimeAmPm,
                    selectedHour = modifyScheduleViewModel.startTimeHour,
                    selectedMinute = modifyScheduleViewModel.startTimeMinute
                )

                Text(
                    text = stringResource(R.string.schedule_end_time),
                    style = MaterialTheme.typography.titleMedium
                )

                ScrollTimePicker(
                    selectedAmPm = modifyScheduleViewModel.endTimeAmPm,
                    selectedHour = modifyScheduleViewModel.endTimeHour,
                    selectedMinute = modifyScheduleViewModel.endTimeMinute
                )

                Spacer(modifier = Modifier.weight(1f))

                ModifyScheduleButton(
                    modifyScheduleViewModel = modifyScheduleViewModel,
                    monthlyScheduleViewModel = monthlyScheduleViewModel,
                    regionViewModel = regionViewModel,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun ModifyScheduleButton(
    modifyScheduleViewModel: ModifyScheduleViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    regionViewModel: RegionViewModel,
    navController: NavController
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    ElevatedButton(
        onClick = {
            val location = regionViewModel.getRegionLocation(
                modifyScheduleViewModel.firstRegion.value,
                modifyScheduleViewModel.secondRegion.value,
                modifyScheduleViewModel.thirdRegion.value
            )
            val errorResId = ScheduleValidator.validateScheduleInput(
                modifyScheduleViewModel.title.value,
                ScheduleTime(
                    modifyScheduleViewModel.startTimeAmPm.value,
                    modifyScheduleViewModel.startTimeHour.intValue,
                    modifyScheduleViewModel.startTimeMinute.intValue,
                ),
                ScheduleTime(
                    modifyScheduleViewModel.endTimeAmPm.value,
                    modifyScheduleViewModel.endTimeHour.intValue,
                    modifyScheduleViewModel.endTimeMinute.intValue,
                ),
                location
            )

            if (errorResId != 0) {
                coroutineScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, errorResId, Toast.LENGTH_SHORT).show()
                }
            } else {
                coroutineScope.launch(Dispatchers.IO) {
                    modifyScheduleViewModel.modifySchedule(location!!, context)
                    monthlyScheduleViewModel.fetchScheduleList(context)
                }

                navController.popBackStack()
            }
        },
        shape = RoundedAllCornerShape,
        colors = ButtonDefaults.buttonColors(containerColor = LightGreen, contentColor = White),
        elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.schedule_modify_button),
            color = White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
