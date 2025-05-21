package com.example.myschedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.example.myschedule.components.bottomnav.BtmNavBar
import com.example.myschedule.ui.theme.DefaultAllPadding
import com.example.myschedule.ui.theme.LightGreen
import com.example.myschedule.ui.theme.RoundedAllCornerShape
import com.example.myschedule.ui.theme.White

@Composable
fun AppScreenContainer(
    navController: NavController,
    currentRoute: String,
    content: @Composable BoxScope.() -> Unit
) {
    Scaffold(
        bottomBar = { BtmNavBar(navController, currentRoute) },
        containerColor = LightGreen
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(DefaultAllPadding)
                .clip(RoundedAllCornerShape).background(White)
        ) {
            content()
        }
    }
}