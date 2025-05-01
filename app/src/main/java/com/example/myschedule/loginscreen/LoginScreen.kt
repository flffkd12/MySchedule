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
import com.example.myschedule.viewmodels.MonthlyScheduleViewModel
import com.example.myschedule.viewmodels.UserViewModel

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
        Spacer(modifier = Modifier.weight(1f))

        LoginBranding()

        Spacer(modifier = Modifier.weight(2f))

        GoogleSignInButton(userViewModel, monthlyScheduleViewModel, navController)

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun LoginBranding() {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.login_app_name),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.login_app_description),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(0.4f).aspectRatio(1.25f)
        )
    }
}
