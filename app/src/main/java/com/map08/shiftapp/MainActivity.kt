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

                CompositionLocalProvider(
                    LocalAuthViewModel provides authViewModel,
                    LocalNavController provides navController,
                    LocalEmployeeViewModel provides employeeViewModel
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Navigation(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

//@Composable
//fun MainScreen() {
//    var showSplash by remember { mutableStateOf(true) }
//
//    LaunchedEffect(key1 = true) {
//        delay(3000)
//        showSplash = false
//    }
//
//    if (showSplash) {
//        SplashScreen()
//    } else {
//        LoginScreen()
//    }
//}
//
//@Composable
//fun SplashScreen() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.logo),
//            contentDescription = "App Logo",
//            modifier = Modifier.size(200.dp)
//        )
//    }
//}
