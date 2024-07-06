package com.map08.shiftapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.map08.shiftapp.pages.*
import com.map08.shiftapp.viewmodels.EmployeeProfileViewModel

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, navController: NavHostController, authViewModel: AuthViewModel) {
    val employeeProfileViewModel: EmployeeProfileViewModel = viewModel()

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
        composable("editProfile") {
            EmployeeEditPage(modifier, navController, employeeProfileViewModel)
        }
        composable("createProfile") {
            CreateProfilePage(modifier, navController, employeeProfileViewModel)
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
