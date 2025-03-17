package com.example.myschedule

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myschedule.ui.theme.LightGreen

@Composable
fun BtmNavBar(navController: NavController, currentBtmBar: String) {

    val btmNavBarItems = listOf(
        BtmNavBarItem(
            title = "일정 추가",
            icon = painterResource(R.drawable.icon_schedule_24),
            route = Routes.CREATE_SCHEDULE
        ),
        BtmNavBarItem(
            title = "메인 화면",
            icon = painterResource(R.drawable.icon_home_24),
            route = Routes.MAIN_SCREEN
        ),
        BtmNavBarItem(
            title = "월별 일정",
            icon = painterResource(R.drawable.icon_calendar_24),
            route = Routes.MONTHLY_SCHEDULE
        )
    )

    BottomAppBar(
        containerColor = Color.White,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.height(84.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            btmNavBarItems.forEach { item ->
                val isClicked = currentBtmBar == item.route

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight().weight(1f).padding(top = 8.dp)
                        .clickable { navController.navigate(item.route) }
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Icon(
                        painter = item.icon,
                        contentDescription = item.title,
                        tint = if (isClicked) LightGreen else Color.Gray,
                        modifier = Modifier.size(28.dp)
                    )

                    Text(
                        text = item.title,
                        color = if (isClicked) LightGreen else Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}