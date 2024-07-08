package com.map08.shiftapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.map08.shiftapp.ui.theme.ShiftAppTheme
import com.map08.shiftapp.viewmodels.AuthViewModel
import com.map08.shiftapp.viewmodels.EmployeeViewModel
import com.map08.shiftapp.viewmodels.ShiftViewModel
import androidx.compose.runtime.CompositionLocalProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ShiftAppTheme {
                val navController = rememberNavController()
                val authViewModel = viewModel<AuthViewModel>()
                val employeeViewModel = viewModel<EmployeeViewModel>()
                val shiftViewModel = viewModel<ShiftViewModel>()

                CompositionLocalProvider(
                    LocalAuthViewModel provides authViewModel,
                    LocalNavController provides navController,
                    LocalEmployeeViewModel provides employeeViewModel,
                    LocalShiftViewModel provides shiftViewModel
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Navigation(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}
