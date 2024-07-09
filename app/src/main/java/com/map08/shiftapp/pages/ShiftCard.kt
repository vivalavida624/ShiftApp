// ShiftCard.kt
package com.map08.shiftapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.map08.shiftapp.R
import com.map08.shiftapp.models.Employee
import com.map08.shiftapp.models.Shift
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ShiftCard(shift: Shift, onUpdateShift: ((Shift) -> Unit)? = null) {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    var cardColor by remember { mutableStateOf(if (shift.status == "complete") Color(0xFF81C784) else Color(0xFFF8F8F8)) }
    val statusColor = if (shift.status == "complete") Color.White else Color(0xFFF44336)

    var showDialog by remember { mutableStateOf(false) }
    var employee by remember { mutableStateOf<Employee?>(null) }

    // Fetch employee data
    LaunchedEffect(shift.employeeId) {
        val db = FirebaseFirestore.getInstance()
        val employeeSnapshot = db.collection("employees").document(shift.employeeId).get().await()
        employee = employeeSnapshot.toObject(Employee::class.java)
    }

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
            // Display employee information
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    employee?.profileImageUrl?.let { imageUrl ->
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = "Employee Profile Picture",
                            contentScale = ContentScale.Crop, // Crop the image
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape) // Clip to circle
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = employee?.name ?: "",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (shift.status == "complete") Color.White else Color.Black
                        )
                        Text(
                            text = employee?.email ?: "",
                            fontSize = 14.sp,
                            color = if (shift.status == "complete") Color.White else Color.Black
                        )
                    }
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
                            .width(80.dp) // Adjust the width as needed
                            .height(80.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Display shift information
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
