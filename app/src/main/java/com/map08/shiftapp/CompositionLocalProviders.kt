package com.map08.shiftapp

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.map08.shiftapp.viewmodels.AuthViewModel

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavController provided")
}
val LocalAuthViewModel = compositionLocalOf<AuthViewModel> {
    error("No AuthViewModel provided")
}
