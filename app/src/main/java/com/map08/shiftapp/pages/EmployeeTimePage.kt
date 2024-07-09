package com.map08.shiftapp.pages

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.firebase.firestore.FirebaseFirestore
import com.map08.shiftapp.LocalShiftViewModel
import kotlinx.coroutines.launch

@Composable
fun EmployeeTimePage(modifier: Modifier = Modifier) {
    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current
    val shiftViewModel = LocalShiftViewModel.current
    val shifts by shiftViewModel.monthlyShifts.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0XFF1976D2)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Employee Time",
            fontSize = 30.sp,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            factory = { CalendarView(context).apply {
                setOnDateChangeListener { _, year, month, dayOfMonth ->
                    selectedDate = "$dayOfMonth/${month + 1}/$year"
                }
            } }
        )

        Text(
            text = "Selected Date: $selectedDate",
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(shifts) { shift ->
                ShiftCard(shift = shift, onUpdateShift = { updatedShift ->
                    coroutineScope.launch {
                        db.collection("shifts")
                            .document(updatedShift.id)
                            .update("status", updatedShift.status)
                            .addOnSuccessListener {
                                shiftViewModel.fetchShiftsForCurrentMonth()
                            }
                            .addOnFailureListener { e ->
                                // Handle failure
                            }
                    }
                })
            }
        }
    }
}
