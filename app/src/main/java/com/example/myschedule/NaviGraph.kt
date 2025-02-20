package com.example.myschedule

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myschedule.login.Login
import com.example.myschedule.login.LoginViewModel

@Composable
fun NaviGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val loginViewModel: LoginViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(route = Routes.LOGIN) { Login(loginViewModel) }
    }
}