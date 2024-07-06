package com.map08.shiftapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.map08.shiftapp.ui.theme.ShiftAppTheme
import com.map08.shiftapp.viewmodels.EmployeeProfileViewModel
import kotlinx.coroutines.delay
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel: AuthViewModel by viewModels()
        val employeeProfileViewModel: EmployeeProfileViewModel by viewModels()
        setContent {
            ShiftAppTheme {
                MainContent(authViewModel, employeeProfileViewModel)
            }
        }
    }
}

@Composable
fun MainContent(authViewModel: AuthViewModel, employeeProfileViewModel: EmployeeProfileViewModel) {
    val navController = rememberNavController()
    val currentUser = FirebaseAuth.getInstance().currentUser
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        delay(3000)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {
        if (currentUser != null) {
            LaunchedEffect(currentUser) {
                employeeProfileViewModel.fetchEmployeeProfile(currentUser.uid)
            }
            val employee by employeeProfileViewModel.employee.collectAsState()

            LaunchedEffect(employee) {
                if (employee == null) {
                    navController.navigate("createProfile")
                } else {
                    navController.navigate("home")
                }
            }
        } else {
            navController.navigate("login")
        }

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MyAppNavigation(modifier = Modifier.padding(innerPadding), navController = navController, authViewModel = authViewModel)
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp)
        )
    }
}
