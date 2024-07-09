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
import com.map08.shiftapp.LocalAuthViewModel
import com.map08.shiftapp.LocalNavController
import com.map08.shiftapp.viewmodels.AuthState
import com.map08.shiftapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerLoginPage() {

    val navController = LocalNavController.current
    val authViewModel = LocalAuthViewModel.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate("manager-home")
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D47A1))  // 使用更深的蓝色背景
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Login Image",
            modifier = Modifier
                .size(150.dp)  // 调整 Logo 大小
                .clip(RoundedCornerShape(12.dp)) // 添加圆角，使图像更美观
                .background(Color.White) // 使用白色背景使 Logo 更明显
                .padding(16.dp) // 添加内边距以提供一些空间
        )

        Text(
            text = "Manager Portal",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,  // 使用白色字体以更好地与背景对比
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email", color = Color.White) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth(0.85f)  // 调整宽度，使其不占满整个屏幕
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password", color = Color.White) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth(0.85f)  // 调整宽度，使其不占满整个屏幕
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))  // 增加间距

        Button(
            onClick = { authViewModel.login(email, password) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth(0.85f)  // 调整宽度，使其不占满整个屏幕
                .height(50.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Text(text = "Login", color = Color(0xFF0D47A1), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Don't have an account?", color = Color.White, fontSize = 14.sp)
            TextButton(onClick = { navController.navigate("manager-signup") }) {
                Text(text = "Sign Up here", color = Color.Yellow, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text(text = "Employee Login", color = Color.Yellow, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
