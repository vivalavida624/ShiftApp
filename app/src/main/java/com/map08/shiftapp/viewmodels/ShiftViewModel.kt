package com.map08.shiftapp.viewmodels

import androidx.lifecycle.ViewModel
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

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startOfMonth = calendar.time

        calendar.add(Calendar.MONTH, 1)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val endOfMonth = calendar.time

        db.collection("shifts")
            .whereEqualTo("employeeId", userId)
            .whereGreaterThanOrEqualTo("startTime", startOfMonth)
            .whereLessThanOrEqualTo("endTime", endOfMonth)
            .get()
            .addOnSuccessListener { documents ->
                val shiftList = documents.mapNotNull { it.toObject(Shift::class.java) }
                _shifts.value = shiftList
            }
            .addOnFailureListener {
                _shifts.value = emptyList()
            }
    }
}
