package com.map08.shiftapp.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.map08.shiftapp.models.Shift
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ShiftCard(shift: Shift, onUpdateShift: ((Shift) -> Unit)? = null) {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val statusColor = if (shift.status == "complete") Color(0xFF4CAF50) else Color(0xFFF44336)

    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = statusColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Start Time: ${sdf.format(shift.startTime.toDate())}", fontSize = 16.sp, color = Color.White)
            Text(text = "End Time: ${sdf.format(shift.endTime.toDate())}", fontSize = 16.sp, color = Color.White)
            Text(text = "Status: ${shift.status}", fontSize = 16.sp, color = Color.White)

            if (shift.status == "Incomplete" && onUpdateShift != null) {
                Button(onClick = {
                    showDialog = true
                }) {
                    Text("Mark as Complete")
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Confirm Completion") },
            text = { Text("Are you sure you want to mark this shift as complete?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onUpdateShift?.invoke(shift.copy(status = "complete"))
                        showDialog = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}
