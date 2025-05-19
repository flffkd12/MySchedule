package com.example.myschedule.ui.mainscreen

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
            val selectRegionGuide = stringResource(R.string.select_region)
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
                    text = stringResource(R.string.my_region),
                    style = MaterialTheme.typography.titleMedium,
                    color = Black
                )

                Spacer(modifier = Modifier.weight(1f))

                val regionSelectionGuide = stringResource(R.string.select_region)
                val isAllRegionSelected = thirdRegion.value != regionSelectionGuide

                TextButton(
                    onClick = {
                        if (isAllRegionSelected) {
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
                                Toast.makeText(context, regionSelectionGuide, Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.complete),
                        style = MaterialTheme.typography.titleMedium,
                        color = Black
                    )
                }
            }

            Text(
                text = stringResource(R.string.select_region_guide),
                style = MaterialTheme.typography.bodyLarge,
                color = Black,
            )

            SelectRegion(firstRegion, secondRegion, thirdRegion, regionViewModel)
        }
    }
}
