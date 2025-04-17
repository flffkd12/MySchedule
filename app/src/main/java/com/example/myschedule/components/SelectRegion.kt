package com.example.myschedule.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myschedule.R
import com.example.myschedule.RegionViewModel
import com.example.myschedule.ui.theme.LightGray
import com.example.myschedule.ui.theme.LightGreen
import com.example.myschedule.ui.theme.RoundedAllCornerShape
import com.example.myschedule.ui.theme.White

@Composable
fun SelectRegion(
    firstRegion: MutableState<String>,
    secondRegion: MutableState<String>,
    thirdRegion: MutableState<String>,
    regionViewModel: RegionViewModel,
) {

    val firstRegionList = regionViewModel.getFirstLevelRegions()
    RegionDropdownMenu(firstRegionList, firstRegion)

    if (firstRegion.value != stringResource(R.string.select_region_guide)) {
        val secondRegionList = regionViewModel.getSecondLevelRegions(firstRegion.value)
        RegionDropdownMenu(secondRegionList, secondRegion)

        if (secondRegion.value != stringResource(R.string.select_region_guide)) {
            val thirdRegionList =
                regionViewModel.getThirdLevelRegions(firstRegion.value, secondRegion.value)
            RegionDropdownMenu(thirdRegionList, thirdRegion)
        }
    }
}

@Composable
fun RegionDropdownMenu(regionList: List<String>, regionName: MutableState<String>) {

    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = regionName.value,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = LightGray,
                focusedBorderColor = LightGreen,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shape = RoundedAllCornerShape,
            containerColor = White,
            modifier = Modifier.height(150.dp)
        ) {
            regionList.forEach { region ->
                DropdownMenuItem(
                    text = { Text(region) },
                    onClick = {
                        regionName.value = region
                        expanded = false
                    }
                )
            }
        }
    }
}