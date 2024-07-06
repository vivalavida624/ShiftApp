package com.map08.shiftapp.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.map08.shiftapp.LocalAuthViewModel
import com.map08.shiftapp.LocalEmployeeProfileViewModel
import com.map08.shiftapp.LocalNavController
import com.map08.shiftapp.models.Employee
import com.map08.shiftapp.viewmodels.EmployeeProfileViewModel

@Composable
fun EmployeeEditPage() {

    val authViewModel = LocalAuthViewModel.current
    val employeeProfileViewModel = LocalEmployeeProfileViewModel.current
    val employee by employeeProfileViewModel.employee.collectAsState(initial = null)
    val navController = LocalNavController.current

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var hobbies by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }


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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Edit Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)

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
                    name = name,
                    age = age.toIntOrNull() ?: 0,
                    country = country,
                    city = city,
                    hobbies = hobbies,
                    email = email,
                    phone = phone
                )
                employeeProfileViewModel.updateEmployeeProfile(updatedEmployee)
                navController.popBackStack()
            }
        ) {
            Text(text = "Save")
        }
    }
}
