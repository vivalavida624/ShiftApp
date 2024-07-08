package com.map08.shiftapp.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.map08.shiftapp.LocalAuthViewModel
import com.map08.shiftapp.LocalNavController
import com.map08.shiftapp.viewmodels.AuthState
import com.map08.shiftapp.viewmodels.AuthViewModel
import com.map08.shiftapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeSignupPage() {
    val navController = LocalNavController.current
    val authViewModel = LocalAuthViewModel.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                navController.navigate("createProfile")
            }
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF1976D2))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Login Image",
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(100.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Employee Portal", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Signup Page", fontSize = 28.sp, color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email", color = Color.White) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password", color = Color.White) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { authViewModel.signup(email, password) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(text = "Create Account", color = Color(0XFF1976D2))
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text(text = "Already have an account? Login", color = Color.White)
        }
    }
}
