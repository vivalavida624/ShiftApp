package com.map08.shiftapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.map08.shiftapp.LocalShiftViewModel
import com.map08.shiftapp.models.Shift
import kotlinx.coroutines.launch

@Composable
fun EmployeeHomeScreen() {
    val shiftViewModel = LocalShiftViewModel.current
    val shifts by shiftViewModel.weeklyShifts.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF1976D2)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(shifts) { shift ->
                ShiftCard(shift = shift, onUpdateShift = { updatedShift ->
                    coroutineScope.launch {
                        db.collection("shifts")
                            .document(updatedShift.id)
                            .update("status", updatedShift.status)
                            .addOnSuccessListener {
                                shiftViewModel.fetchShiftsForCurrentWeek()
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
