package com.map08.shiftapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.map08.shiftapp.pages.EmployeeHomePage
import com.map08.shiftapp.pages.EmployeeLoginPage
import com.map08.shiftapp.pages.EmployeeSignupPage
import com.map08.shiftapp.pages.ManagerHomePage
import com.map08.shiftapp.pages.ManagerLoginPage
import com.map08.shiftapp.pages.ManagerSignupPage

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, navController: NavHostController, authViewModel: AuthViewModel) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            EmployeeLoginPage(modifier, navController, authViewModel)
        }
        composable("signup") {
            EmployeeSignupPage(modifier, navController, authViewModel)
        }
        composable("home") {
            EmployeeHomePage(modifier, navController, authViewModel)
        }
        composable("manager-login") {
            ManagerLoginPage(modifier, navController, authViewModel)
        }
        composable("manager-signup") {
            ManagerSignupPage(modifier, navController, authViewModel)
        }
        composable("manager-home") {
            ManagerHomePage(modifier, navController, authViewModel)
        }
    }
}
