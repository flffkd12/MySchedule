package com.example.myschedule.components.bottomnav

import androidx.compose.ui.graphics.painter.Painter

data class BtmNavBarItem(
    val title: String,
    val icon: Painter,
    val route: String
)
