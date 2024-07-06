package com.map08.shiftapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.map08.shiftapp.LocalAuthViewModel
import com.map08.shiftapp.LocalEmployeeProfileViewModel
import com.map08.shiftapp.LocalNavController
import com.map08.shiftapp.R
import com.map08.shiftapp.models.Employee
import com.map08.shiftapp.viewmodels.EmployeeProfileViewModel

@Composable
    fun EmployeeProfilePage() {

        val authViewModel = LocalAuthViewModel.current
        val employeeProfileViewModel = LocalEmployeeProfileViewModel.current
        val employee by employeeProfileViewModel.employee.collectAsState(initial = null)
        val navController = LocalNavController.current

        if (employee != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0XFF1976D2))
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile_placeholder),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(100.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Profile",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                )
                Text(text = "Name: ${employee?.name ?: ""}", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White)
                Text(text = "Age: ${employee?.age ?: ""}", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White)
                Text(text = "Country: ${employee?.country ?: ""}", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White)
                Text(text = "City: ${employee?.city ?: ""}", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White)
                Text(text = "Hobbies: ${employee?.hobbies ?: ""}", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White)
                Text(text = "Email: ${employee?.email ?: ""}", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White)
                Text(text = "Phone: ${employee?.phone ?: ""}", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        navController.navigate("editProfile")
                    }
                ) {
                    Text(text = "Edit Profile")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.navigate("createProfile")
                    }
                ) {
                    Text(text = "Create Profile")
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }