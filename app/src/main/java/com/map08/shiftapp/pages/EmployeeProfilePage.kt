package com.map08.shiftapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.map08.shiftapp.LocalAuthViewModel
import com.map08.shiftapp.LocalEmployeeViewModel
import com.map08.shiftapp.LocalNavController
import com.map08.shiftapp.R

@Composable
fun EmployeeProfilePage() {

    val authViewModel = LocalAuthViewModel.current
    val employeeProfileViewModel = LocalEmployeeViewModel.current
    val employee by employeeProfileViewModel.employee.collectAsState(initial = null)
    val navController = LocalNavController.current

    if (employee != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0XFF1976D2))
                .verticalScroll(rememberScrollState()) // 添加垂直滚动功能
                .padding(top = 100.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val painter = rememberAsyncImagePainter(
                model = employee?.profileImageUrl,
                placeholder = painterResource(id = R.drawable.ic_profile_placeholder),
                error = painterResource(id = R.drawable.ic_profile_placeholder)
            )

            Image(
                painter = painter,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(80.dp))
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Profile",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Text(
                text = "Name: ${employee?.name ?: ""}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Age: ${employee?.age ?: ""}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Country: ${employee?.country ?: ""}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "City: ${employee?.city ?: ""}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Hobbies: ${employee?.hobbies ?: ""}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Email: ${employee?.email ?: ""}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Phone: ${employee?.phone ?: ""}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate("editProfile")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0XFF1976D2)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Text(text = "Edit Profile", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

//            Button(
//                onClick = {
//                    navController.navigate("createProfile")
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0XFF1976D2)),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
//                    .clip(RoundedCornerShape(12.dp))
//            ) {
//                Text(text = "Create Profile", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}
