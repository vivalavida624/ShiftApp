package com.map08.shiftapp.pages

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import java.util.*

@Composable
fun EmployeeTimePage() {
    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF1976D2)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calendar",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { CalendarView(context).apply {
                setOnDateChangeListener { _, year, month, dayOfMonth ->
                    selectedDate = "$dayOfMonth/${month + 1}/$year"
                }
            } }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selected Date: $selectedDate",
            fontSize = 20.sp,
            color = Color.White
        )
    }
}
