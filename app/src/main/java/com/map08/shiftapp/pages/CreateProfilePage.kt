package com.map08.shiftapp.pages

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.map08.shiftapp.LocalEmployeeViewModel
import com.map08.shiftapp.LocalNavController
import com.map08.shiftapp.R
import com.map08.shiftapp.models.Employee
import com.map08.shiftapp.utils.uploadImageToFirebase
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfilePage() {
    val employeeProfileViewModel = LocalEmployeeViewModel.current
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
            .background(Color(0XFF1976D2))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Create Profile", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name, onValueChange = { name = it },
            label = { Text("Name", color = Color.White) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.White)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age, onValueChange = { age = it },
            label = { Text("Age", color = Color.White) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.White)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = country, onValueChange = { country = it },
            label = { Text("Country", color = Color.White) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.White)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = city, onValueChange = { city = it },
            label = { Text("City", color = Color.White) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.White)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = hobbies, onValueChange = { hobbies = it },
            label = { Text("Hobbies", color = Color.White) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.White)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text("Email", color = Color.White) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.White)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone, onValueChange = { phone = it },
            label = { Text("Phone", color = Color.White) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (profileImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(profileImageUri),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(100.dp))
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_placeholder),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(100.dp))
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
                    age = age.toIntOrNull() ?: 0,
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
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Save", color = Color(0XFF1976D2))
        }
    }
}
