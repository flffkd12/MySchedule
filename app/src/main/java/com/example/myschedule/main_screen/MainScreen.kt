package com.example.myschedule.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.myschedule.UserViewModel
import com.example.myschedule.ui.theme.LightGreen

@Composable
fun MainScreen(userViewModel: UserViewModel, navController: NavController) {
    val userName by userViewModel.userName.collectAsState()

    Scaffold(
        bottomBar = { BtmNavBar(navController) },
        containerColor = LightGreen
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("${userName}님 반갑습니다", color = Color.White)
        }
    }
}