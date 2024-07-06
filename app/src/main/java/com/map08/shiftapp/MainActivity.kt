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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.map08.shiftapp.ui.theme.ShiftAppTheme
import com.map08.shiftapp.viewmodels.AuthViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ShiftAppTheme {
                val navController = rememberNavController()
                val authViewModel = viewModel<AuthViewModel>()

                CompositionLocalProvider(
                    LocalAuthViewModel provides authViewModel,
                    LocalNavController provides navController,
                    ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Navigation(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        delay(3000)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {
        LoginScreen()
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
