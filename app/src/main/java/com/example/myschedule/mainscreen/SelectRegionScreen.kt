package com.example.myschedule.mainscreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschedule.R
import com.example.myschedule.components.SelectRegion
import com.example.myschedule.ui.theme.Black
import com.example.myschedule.ui.theme.DefaultHorizontalPadding
import com.example.myschedule.viewmodels.RegionViewModel
import com.example.myschedule.viewmodels.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SelectRegionScreen(
    regionViewModel: RegionViewModel,
    weatherViewModel: WeatherViewModel,
    navController: NavController
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(containerColor = Color.Transparent) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .padding(horizontal = DefaultHorizontalPadding)
        ) {
            val selectRegionGuide = stringResource(R.string.select_region_guide)
            val firstRegion = rememberSaveable { mutableStateOf(selectRegionGuide) }
            val secondRegion = rememberSaveable { mutableStateOf(selectRegionGuide) }
            val thirdRegion = rememberSaveable { mutableStateOf(selectRegionGuide) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "내 지역",
                    style = MaterialTheme.typography.titleMedium,
                    color = Black
                )

                Spacer(modifier = Modifier.weight(1f))

                TextButton(
                    onClick = {
                        if (thirdRegion.value != "지역을 선택해주세요") {
                            weatherViewModel.setRegionName(firstRegion.value + " " + secondRegion.value + " " + thirdRegion.value)
                            weatherViewModel.setRegionLocation(
                                regionViewModel.getRegionLocation(
                                    firstRegion.value,
                                    secondRegion.value,
                                    thirdRegion.value
                                )!!
                            )
                            navController.popBackStack()
                        } else {
                            coroutineScope.launch(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "지역을 전부 선택해주세요",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                ) {
                    Text(
                        text = "완료",
                        style = MaterialTheme.typography.titleMedium,
                        color = Black
                    )
                }
            }

            Text(
                text = "날씨를 확인할 지역을 찾습니다.",
                style = MaterialTheme.typography.bodyLarge,
                color = Black,
            )

            SelectRegion(firstRegion, secondRegion, thirdRegion, regionViewModel)
        }
    }
}
