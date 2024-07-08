package com.map08.shiftapp

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.map08.shiftapp.viewmodels.AuthViewModel
import com.map08.shiftapp.viewmodels.EmployeeViewModel
import com.map08.shiftapp.viewmodels.ShiftViewModel

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavController provided")
}
val LocalAuthViewModel = compositionLocalOf<AuthViewModel> {
    error("No AuthViewModel provided")
}
val LocalEmployeeViewModel = compositionLocalOf<EmployeeViewModel> {
    error("No EmployeeViewModel provided")
}
val LocalShiftViewModel = compositionLocalOf<ShiftViewModel> {
    error("No ShiftViewModel provided")
}
