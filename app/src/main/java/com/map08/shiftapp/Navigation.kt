package com.map08.shiftapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.map08.shiftapp.pages.*
import com.map08.shiftapp.viewmodels.AuthViewModel

@Composable
fun Navigation() {

    val navController = LocalNavController.current
    val authViewModel = LocalAuthViewModel.current

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            EmployeeLoginPage()
        }
        composable("signup") {
            EmployeeSignupPage()
        }
        composable("home") {
            EmployeeHomePage()
        }
        composable("profile") {
            EmployeeProfilePage()
        }
        composable("editProfile") {
            EmployeeEditPage()
        }
        composable("manager-login") {
            ManagerLoginPage()
        }
        composable("manager-signup") {
            ManagerSignupPage()
        }
        composable("manager-home") {
            ManagerHomePage()
        }

    }
}
