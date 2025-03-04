package com.example.myschedule.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        Column(
            modifier = Modifier.padding(innerPadding).padding(horizontal = 24.dp).fillMaxSize()
        ) {
            Box(modifier = Modifier.weight(0.15f).fillMaxWidth().background(LightGreen)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(Modifier.weight(1f))
                    Text("${userName}님 반갑습니다", color = Color.White)
                }
            }
            Box(
                modifier = Modifier.weight(0.85f).fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(Color.White)
            ) {

            }
        }
    }
}