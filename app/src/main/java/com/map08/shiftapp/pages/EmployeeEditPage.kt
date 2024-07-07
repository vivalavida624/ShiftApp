package com.map08.shiftapp.pages

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.storage.FirebaseStorage
import com.map08.shiftapp.LocalEmployeeProfileViewModel
import com.map08.shiftapp.LocalNavController
import com.map08.shiftapp.models.Employee
import com.map08.shiftapp.utils.uploadImageToFirebase // Import the function from the utils package
import java.util.*

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
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // 添加垂直滚动功能
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Edit Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        if (profileImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(profileImageUri),
                contentDescription = "Profile Image",
                modifier = Modifier.size(128.dp)
            )
        } else if (employee?.profileImageUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(employee?.profileImageUrl),
                contentDescription = "Profile Image",
                modifier = Modifier.size(128.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { launcher.launch("image/*") }) {
            Text("Select Profile Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") }
        )
        TextField(
            value = country,
            onValueChange = { country = it },
            label = { Text("Country") }
        )
        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") }
        )
        TextField(
            value = hobbies,
            onValueChange = { hobbies = it },
            label = { Text("Hobbies") }
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val updatedEmployee = Employee(
                    id = employee?.id ?: "",  // 确保包括员工的ID
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
            }
        ) {
            Text(text = "Save")
        }
    }
}
