package com.example.myschedule.schedulecreate.titletimeinput

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import com.example.myschedule.R
import com.example.myschedule.ui.theme.Black
import com.example.myschedule.ui.theme.LightGray
import com.example.myschedule.ui.theme.LightGreen

@Composable
fun ScheduleTitle(titleName: MutableState<String>) {

    val focusManager = LocalFocusManager.current

    Text(
        text = stringResource(R.string.title_input_guide),
        color = Black,
        style = MaterialTheme.typography.titleLarge
    )

    OutlinedTextField(
        value = titleName.value,
        onValueChange = { titleName.value = it },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = LightGray,
            focusedBorderColor = LightGreen,
            cursorColor = LightGreen,
            selectionColors = TextSelectionColors(
                handleColor = LightGreen,
                backgroundColor = LightGreen
            )
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        text = stringResource(R.string.title_limit_guide),
        color = LightGray,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Right,
        modifier = Modifier.fillMaxWidth()
    )
}