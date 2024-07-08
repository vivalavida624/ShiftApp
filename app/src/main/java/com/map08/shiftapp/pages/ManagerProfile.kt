package com.map08.shiftapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.map08.shiftapp.LocalNavController
import com.map08.shiftapp.R

@Composable
fun ManagerProfilePage(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0XFF1976D2))
            .padding(top = 100.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Manager Profile",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Text(text = "Name: John Doe", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White, modifier = Modifier.padding(vertical = 4.dp))
        Text(text = "Department: Sales", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White, modifier = Modifier.padding(vertical = 4.dp))
        Text(text = "Experience: 10 years", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White, modifier = Modifier.padding(vertical = 4.dp))

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate("editManagerProfile")
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

        Button(
            onClick = {
                navController.navigate("createManagerProfile")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0XFF1976D2)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Text(text = "Create Profile", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
