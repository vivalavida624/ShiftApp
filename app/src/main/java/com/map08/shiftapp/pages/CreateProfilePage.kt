package com.map08.shiftapp.pages

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.map08.shiftapp.LocalEmployeeProfileViewModel
import com.map08.shiftapp.LocalNavController
import com.map08.shiftapp.models.Employee
import com.map08.shiftapp.utils.uploadImageToFirebase // Import the function from the utils package
import java.util.*

@Composable
fun CreateProfilePage() {
    val employeeProfileViewModel = LocalEmployeeProfileViewModel.current
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

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            profileImageUri = uri
        }
    )

    Column(
        modifier = Modifier
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
        Spacer(modifier = Modifier.height(8.dp))

        if (profileImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(profileImageUri),
                contentDescription = "Profile Image",
                modifier = Modifier.size(128.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { launcher.launch("image/*") }) {
            Text("Select Profile Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val newEmployee = Employee(
                    id = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                    name = name,
                    age = age.toInt(),
                    country = country,
                    city = city,
                    hobbies = hobbies,
                    email = email,
                    phone = phone
                )
                if (profileImageUri != null) {
                    uploadImageToFirebase(context, profileImageUri!!) { downloadUrl ->
                        newEmployee.profileImageUrl = downloadUrl
                        employeeProfileViewModel.createEmployeeProfile(newEmployee)
                        navController.navigate("home")
                    }
                } else {
                    employeeProfileViewModel.createEmployeeProfile(newEmployee)
                    navController.navigate("home")
                }
            }
        ) {
            Text("Save")
        }
    }
}
