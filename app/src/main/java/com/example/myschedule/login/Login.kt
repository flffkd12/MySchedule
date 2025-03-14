package com.example.myschedule.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myschedule.R
import com.example.myschedule.monthlyschedule.MonthlyScheduleViewModel

@Composable
fun Login(
    userViewModel: UserViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(Modifier.weight(1f))
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.app_description),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(12.dp))
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = stringResource(R.string.app_logo),
            modifier = Modifier.fillMaxWidth(0.4f).aspectRatio(1.25f)
        )
        Spacer(Modifier.weight(2f))
        GoogleSignInButton(userViewModel, monthlyScheduleViewModel, navController)
        Spacer(Modifier.weight(1f))
    }
}