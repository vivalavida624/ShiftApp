package com.map08.shiftapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.map08.shiftapp.LocalEmployeeViewModel
import com.map08.shiftapp.R

@Composable
fun EmployeeDetailPage(employeeId: String) {
    val viewModel = LocalEmployeeViewModel.current
    val employee by viewModel.getEmployeeById(employeeId).collectAsState(initial = null)

    employee?.let { emp ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Name: ${emp.name}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = "Age: ${emp.age}", fontSize = 20.sp)
            Text(text = "Country: ${emp.country}", fontSize = 20.sp)
            Text(text = "City: ${emp.city}", fontSize = 20.sp)
            Text(text = "Hobbies: ${emp.hobbies}", fontSize = 20.sp)
            Text(text = "Email: ${emp.email}", fontSize = 20.sp)
            Text(text = "Phone: ${emp.phone}", fontSize = 20.sp)
            emp.profileImageUrl?.let { imageUrl ->
                val painter = rememberAsyncImagePainter(
                    model = imageUrl,
                    placeholder = painterResource(id = R.drawable.ic_profile_placeholder),
                    error = painterResource(id = R.drawable.ic_profile_placeholder)
                )
                Image(
                    painter = painter,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(RoundedCornerShape(64.dp))
                        .padding(top = 16.dp)
                )
            }
        }
    } ?: run {
        Text(text = "Loading...", fontSize = 24.sp, modifier = Modifier.fillMaxSize())
    }
}
