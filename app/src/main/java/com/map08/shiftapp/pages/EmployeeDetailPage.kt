package com.map08.shiftapp.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.map08.shiftapp.LocalEmployeeViewModel
import com.map08.shiftapp.LocalNavController
import com.map08.shiftapp.R
import java.util.Date

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
                onDismiss = { showDialog.value = false },
                onShiftSelected = { startTime, endTime ->
                    // 处理选择的 shift，可以在这里进行相应的逻辑处理
                    showDialog.value = false
                }
            )
        }
    }
}

@Composable
fun AssignShiftDialog(
    onDismiss: () -> Unit,
    onShiftSelected: (startTime: Date, endTime: Date) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Assign Shift") },
        confirmButton = {
            Button(
                onClick = { /* 在此处处理选择 shift 的逻辑 */ },
            ) {
                Text("Assign")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
            ) {
                Text("Cancel")
            }
        }
    )
}