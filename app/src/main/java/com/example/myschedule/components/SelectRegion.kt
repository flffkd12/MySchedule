package com.example.myschedule.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myschedule.R
import com.example.myschedule.ui.theme.LightGray
import com.example.myschedule.ui.theme.LightGreen
import com.example.myschedule.ui.theme.RoundedAllCornerShape
import com.example.myschedule.ui.theme.White
import com.example.myschedule.viewmodels.RegionViewModel

@Composable
fun SelectRegion(
    firstRegion: MutableState<String>,
    secondRegion: MutableState<String>,
    thirdRegion: MutableState<String>,
    regionViewModel: RegionViewModel,
) {

    val firstRegionList = regionViewModel.getFirstLevelRegions()
    RegionDropdownMenu(firstRegionList, firstRegion)

    if (firstRegion.value != stringResource(R.string.select_region)) {
        val secondRegionList = regionViewModel.getSecondLevelRegions(firstRegion.value)
        RegionDropdownMenu(secondRegionList, secondRegion)

        if (secondRegion.value != stringResource(R.string.select_region)) {
            val thirdRegionList =
                regionViewModel.getThirdLevelRegions(firstRegion.value, secondRegion.value)
            RegionDropdownMenu(thirdRegionList, thirdRegion)
        }
    }
}

@Composable
fun RegionDropdownMenu(regionList: List<String>, regionName: MutableState<String>) {

    Box {
        val focusRequester = remember { FocusRequester() }
        var expanded by remember { mutableStateOf(false) }

        OutlinedTextField(
            value = regionName.value,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                IconButton(
                    onClick = {
                        expanded = !expanded
                        focusRequester.requestFocus()
                    }
                ) {
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
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shape = RoundedAllCornerShape,
            containerColor = White,
            modifier = Modifier.heightIn(max = 200.dp)
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
