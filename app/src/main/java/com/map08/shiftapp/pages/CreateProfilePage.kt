package com.map08.shiftapp.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.map08.shiftapp.models.Employee
import com.map08.shiftapp.viewmodels.EmployeeProfileViewModel

@Composable
fun CreateProfilePage(modifier: Modifier = Modifier, navController: NavController, viewModel: EmployeeProfileViewModel) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var hobbies by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Create Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Age") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = country, onValueChange = { country = it }, label = { Text("Country") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("City") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = hobbies, onValueChange = { hobbies = it }, label = { Text("Hobbies") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val newEmployee = Employee(
                    id = "",
                    name = name,
                    age = age.toInt(),
                    country = country,
                    city = city,
                    hobbies = hobbies,
                    email = email,
                    phone = phone
                )
                viewModel.createEmployeeProfile(newEmployee)
                navController.navigate("home")
            }
        ) {
            Text("Save")
        }
    }
}

