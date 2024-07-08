package com.map08.shiftapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.map08.shiftapp.models.Shift
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class ShiftViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _shifts = MutableStateFlow<List<Shift>>(emptyList())
    val shifts: StateFlow<List<Shift>> = _shifts

    init {
        fetchShiftsForCurrentMonth()
    }

    private fun fetchShiftsForCurrentMonth() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        Log.d("MyApp", "Current user ID: $userId")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startOfMonth = Timestamp(calendar.time)
        Log.d("MyApp", "Start of month: $startOfMonth")

        calendar.add(Calendar.MONTH, 1)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val endOfMonth = Timestamp(calendar.time)
        Log.d("MyApp", "End of month: $endOfMonth")

        db.collection("shifts")
            .whereEqualTo("employeeId", userId)
            .whereGreaterThanOrEqualTo("startTime", startOfMonth)
            .whereLessThanOrEqualTo("endTime", endOfMonth)
            .get()
            .addOnSuccessListener { documents ->
                val shiftList = documents.mapNotNull { it.toObject(Shift::class.java) }
                _shifts.value = shiftList
                Log.d("MyApp", "Shifts retrieved: $shiftList")
            }
            .addOnFailureListener { e ->
                _shifts.value = emptyList()
                Log.e("MyApp", "Error fetching shifts", e)
            }
    }
}
