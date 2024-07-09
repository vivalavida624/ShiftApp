package com.map08.shiftapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.map08.shiftapp.R
import com.map08.shiftapp.models.Shift
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ShiftCard(shift: Shift, onUpdateShift: ((Shift) -> Unit)? = null) {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    var cardColor by remember { mutableStateOf(if (shift.status == "complete") Color(0xFF4CAF50) else Color(0xFFF8F8F8)) } // 设置未完成状态下的背景颜色为浅灰色
    val statusColor = if (shift.status == "complete") Color.White else Color(0xFFF44336)

    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Start Time: ${sdf.format(shift.startTime.toDate())}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (shift.status == "complete") Color.White else Color.Black
                    )
                    Text(
                        text = "End Time: ${sdf.format(shift.endTime.toDate())}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (shift.status == "complete") Color.White else Color.Black
                    )
                    Text(
                        text = "Status: ${shift.status}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }
                if (shift.status == "Incomplete" && onUpdateShift != null) {
                    Button(
                        onClick = {
                            showDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = statusColor),
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .width(100.dp) // Adjust the width as needed
                            .height(36.dp)
                    ) {
                        Text("Done", color = Color.White, fontSize = 14.sp)
                    }
                } else if (shift.status == "complete") {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_stamp),
                        contentDescription = "Stamp",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .width(70.dp) // Adjust the width as needed
                            .height(70.dp)
                    )
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
                        cardColor = Color(0xFF81C784)
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
