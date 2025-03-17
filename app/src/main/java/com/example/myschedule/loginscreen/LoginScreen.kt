package com.example.myschedule.loginscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myschedule.R
import com.example.myschedule.monthlyschedule.MonthlyScheduleViewModel

@Composable
fun LoginScreen(
    userViewModel: UserViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    navController: NavController
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(Modifier.weight(1f))

        LoginBranding()

        Spacer(Modifier.weight(2f))

        GoogleSignInButton(userViewModel, monthlyScheduleViewModel, navController)

        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun LoginBranding() {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.app_description),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(12.dp))

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = stringResource(R.string.app_logo),
            modifier = Modifier.fillMaxWidth(0.4f).aspectRatio(1.25f)
        )
    }
}