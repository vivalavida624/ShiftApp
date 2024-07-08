package com.map08.shiftapp.pages

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.map08.shiftapp.LocalShiftViewModel
import com.map08.shiftapp.models.Shift
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EmployeeTimePage() {
    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current
    val shiftViewModel = LocalShiftViewModel.current
    val shifts by shiftViewModel.shifts.collectAsState()

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

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            items(shifts) { shift ->
                ShiftItem(shift = shift)
            }
        }
    }
}

@Composable
fun ShiftItem(shift: Shift) {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Start Time: ${sdf.format(shift.startTime.toDate())}", fontSize = 16.sp, color = Color.Black)
        Text(text = "End Time: ${sdf.format(shift.endTime.toDate())}", fontSize = 16.sp, color = Color.Black)
        Text(text = "Status: ${shift.status}", fontSize = 16.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
    }
}
