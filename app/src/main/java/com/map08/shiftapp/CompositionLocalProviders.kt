package com.map08.shiftapp

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.map08.shiftapp.viewmodels.AuthViewModel
import com.map08.shiftapp.viewmodels.EmployeeViewModel
import com.map08.shiftapp.viewmodels.ManagerViewModel

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavController provided")
}
val LocalAuthViewModel = compositionLocalOf<AuthViewModel> {
    error("No AuthViewModel provided")
}
val LocalEmployeeViewModel = compositionLocalOf<EmployeeViewModel> {
    error("No EmployeeProfileViewModel provided")
}
val LocalManagerViewModel = compositionLocalOf<ManagerViewModel> {
    error("No ManagerViewModel provided")
}
