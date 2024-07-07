package com.map08.shiftapp.pages

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.map08.shiftapp.LocalEmployeeProfileViewModel
import com.map08.shiftapp.LocalNavController
import com.map08.shiftapp.R
import com.map08.shiftapp.models.Employee
import com.map08.shiftapp.LocalAuthViewModel
import com.map08.shiftapp.utils.uploadImageToFirebase

@Composable
fun EmployeeEditPage() {
    val employeeProfileViewModel = LocalEmployeeProfileViewModel.current
    val employee by employeeProfileViewModel.employee.collectAsState(initial = null)
    val navController = LocalNavController.current
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var hobbies by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(employee) {
        employee?.let {
            name = it.name
            age = it.age.toString()
            country = it.country
            city = it.city
            hobbies = it.hobbies
            email = it.email
            phone = it.phone
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            profileImageUri = uri
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF1976D2))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Profile",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (profileImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(profileImageUri),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(100.dp))
            )
        } else if (employee?.profileImageUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(employee?.profileImageUrl),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(100.dp))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Select Profile Image", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = age,
            onValueChange = { age = it },
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = country,
            onValueChange = { country = it },
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = city,
            onValueChange = { city = it },
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = hobbies,
            onValueChange = { hobbies = it },
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = phone,
            onValueChange = { phone = it },
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val updatedEmployee = Employee(
                    id = employee?.id ?: "",  // Ensure employee's ID is included
                    name = name,
                    age = age.toIntOrNull() ?: 0,
                    country = country,
                    city = city,
                    hobbies = hobbies,
                    email = email,
                    phone = phone
                )
                if (profileImageUri != null) {
                    uploadImageToFirebase(context, profileImageUri!!) { downloadUrl ->
                        updatedEmployee.profileImageUrl = downloadUrl
                        employeeProfileViewModel.updateEmployeeProfile(updatedEmployee)
                        navController.popBackStack()
                    }
                } else {
                    employeeProfileViewModel.updateEmployeeProfile(updatedEmployee)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
