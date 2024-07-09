package com.map08.shiftapp.pages

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.map08.shiftapp.LocalEmployeeViewModel
import com.map08.shiftapp.LocalNavController
import com.map08.shiftapp.R
import com.map08.shiftapp.viewmodels.ShiftViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EmployeeDetailPage(employeeId: String) {
    val navController = LocalNavController.current
    val viewModel = LocalEmployeeViewModel.current
    val employee by viewModel.getEmployeeById(employeeId).collectAsState(initial = null)

    // State 用于控制是否显示弹窗
    val showDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Employee Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        employee?.let { emp ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // 显示员工信息和头像
                emp.profileImageUrl?.let { imageUrl ->
                    val painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                            .apply(block = fun ImageRequest.Builder.() {
                                placeholder(R.drawable.ic_profile_placeholder)
                                error(R.drawable.ic_profile_placeholder)
                            }).build()
                    )
                    Image(
                        painter = painter,
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(140.dp)
                            .clip(RoundedCornerShape(64.dp))

                    )
                }

                // 显示员工其它信息
                Text(text = "Name: ${emp.name}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = "Age: ${emp.age}", fontSize = 20.sp)
                Text(text = "Country: ${emp.country}", fontSize = 20.sp)
                Text(text = "City: ${emp.city}", fontSize = 20.sp)
                Text(text = "Hobbies: ${emp.hobbies}", fontSize = 20.sp)
                Text(text = "Email: ${emp.email}", fontSize = 20.sp)
                Text(text = "Phone: ${emp.phone}", fontSize = 20.sp)

                // 显示分配 shift 的按钮
                Button(
                    onClick = { showDialog.value = true },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Assign Shift")
                }
            }
        } ?: run {
            Text(text = "Loading...", fontSize = 24.sp, modifier = Modifier.fillMaxSize())
        }

        // 弹窗部分
        if (showDialog.value) {
            AssignShiftDialog(
                employeeId = employeeId,
                onDismiss = { showDialog.value = false },
                onShiftAssigned = { showDialog.value = false }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignShiftDialog(
    employeeId: String,
    onDismiss: () -> Unit,
    onShiftAssigned: () -> Unit
) {
    // 用于保存用户选择的日期和时间
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    val dateState = remember { mutableStateOf(calendar.time) }
    val startTimeState = remember { mutableStateOf(calendar.time) }
    val endTimeState = remember { mutableStateOf(calendar.time) }
    val shiftViewModel: ShiftViewModel = viewModel() // 获取ViewModel实例

    // 显示日期选择器
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            dateState.value = calendar.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // 显示开始时间选择器
    val startTimePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            startTimeState.value = calendar.time
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    // 显示结束时间选择器
    val endTimePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            endTimeState.value = calendar.time
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Assign Shift") },
        text = {
            Column {
                Text("Date: ${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dateState.value)}")
                Button(onClick = { datePickerDialog.show() }) {
                    Text("Select Date")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Start Time: ${SimpleDateFormat("HH:mm", Locale.getDefault()).format(startTimeState.value)}")
                Button(onClick = { startTimePickerDialog.show() }) {
                    Text("Select Start Time")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("End Time: ${SimpleDateFormat("HH:mm", Locale.getDefault()).format(endTimeState.value)}")
                Button(onClick = { endTimePickerDialog.show() }) {
                    Text("Select End Time")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    shiftViewModel.assignShift(employeeId, dateState.value, startTimeState.value, endTimeState.value)
                    onShiftAssigned()
                }
            ) {
                Text("Assign")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}