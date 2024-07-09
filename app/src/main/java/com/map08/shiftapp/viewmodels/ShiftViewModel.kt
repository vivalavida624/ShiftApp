package com.map08.shiftapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.map08.shiftapp.models.Shift
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class ShiftViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _monthlyShifts = MutableStateFlow<List<Shift>>(emptyList())
    val monthlyShifts: StateFlow<List<Shift>> = _monthlyShifts

    private val _weeklyShifts = MutableStateFlow<List<Shift>>(emptyList())
    val weeklyShifts: StateFlow<List<Shift>> = _weeklyShifts

    private val _allUsersShiftsForCurrentMonth = MutableStateFlow<List<Shift>>(emptyList())
    val allUsersShiftsForCurrentMonth: StateFlow<List<Shift>> = _allUsersShiftsForCurrentMonth

    private val _allShifts = MutableStateFlow<List<Shift>>(emptyList())
    val allShifts: StateFlow<List<Shift>> = _allShifts

    init {
        fetchShiftsForCurrentMonth()
        fetchShiftsForCurrentWeek()
        fetchShiftsForAllUsersForCurrentMonth()
        fetchAllShiftsForAllUsers() // 为什么这里加上才会显示呢？
    }

    fun fetchShiftsForCurrentMonth() {
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
                val shiftList = documents.mapNotNull { document ->
                    document.toObject(Shift::class.java).copy(id = document.id)
                }
                _monthlyShifts.value = shiftList
                Log.d("MyApp", "Shifts retrieved: $shiftList")
            }
            .addOnFailureListener { e ->
                _monthlyShifts.value = emptyList()
                Log.e("MyApp", "Error fetching shifts", e)
            }
    }

    fun fetchShiftsForCurrentWeek() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        Log.d("MyApp", "Current user ID: $userId")

        val calendar = Calendar.getInstance()

        // Start of the week (Assuming Sunday is the first day of the week)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val startOfWeek = Timestamp(calendar.time)
        Log.d("MyApp", "Start of week: $startOfWeek")

        // End of the week (Saturday)
        calendar.add(Calendar.DAY_OF_WEEK, 6)
        val endOfWeek = Timestamp(calendar.time)
        Log.d("MyApp", "End of week: $endOfWeek")

        db.collection("shifts")
            .whereEqualTo("employeeId", userId)
            .whereGreaterThanOrEqualTo("startTime", startOfWeek)
            .whereLessThanOrEqualTo("endTime", endOfWeek)
            .get()
            .addOnSuccessListener { documents ->
                val shiftList = documents.mapNotNull { document ->
                    document.toObject(Shift::class.java).copy(id = document.id)
                }
                _weeklyShifts.value = shiftList
                Log.d("MyApp", "Shifts retrieved: $shiftList")
            }
            .addOnFailureListener { e ->
                _weeklyShifts.value = emptyList()
                Log.e("MyApp", "Error fetching shifts", e)
            }
    }

    // Function to fetch shifts for all users for the current month
    fun fetchShiftsForAllUsersForCurrentMonth() {
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
            .whereGreaterThanOrEqualTo("startTime", startOfMonth)
            .whereLessThanOrEqualTo("endTime", endOfMonth)
            .get()
            .addOnSuccessListener { documents ->
                val shiftList = documents.mapNotNull { document ->
                    document.toObject(Shift::class.java).copy(id = document.id)
                }
                // Update the StateFlow with the fetched shifts
                _allUsersShiftsForCurrentMonth.value = shiftList
                Log.d("MyApp", "All users' shifts for current month retrieved: $shiftList")
            }
            .addOnFailureListener { e ->
                _allUsersShiftsForCurrentMonth.value = emptyList()
                Log.e("MyApp", "Error fetching all users' shifts for current month", e)
            }
    }

    // Function to fetch all shifts for all users
    fun fetchAllShiftsForAllUsers() {
        db.collection("shifts")
            .get()
            .addOnSuccessListener { documents ->
                val shiftList = documents.mapNotNull { document ->
                    document.toObject(Shift::class.java).copy(id = document.id)
                }
                // Update the StateFlow with the fetched shifts
                _allShifts.value = shiftList
                Log.d("MyApp", "All shifts for all users retrieved: $shiftList")
            }
            .addOnFailureListener { e ->
                _allShifts.value = emptyList()
                Log.e("MyApp", "Error fetching all shifts for all users", e)
            }
    }

    fun assignShift(employeeId: String, date: Date, startTime: Date, endTime: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date

        // 设置开始时间
        val startCalendar = Calendar.getInstance()
        startCalendar.time = startTime
        calendar.set(Calendar.HOUR_OF_DAY, startCalendar.get(Calendar.HOUR_OF_DAY))
        calendar.set(Calendar.MINUTE, startCalendar.get(Calendar.MINUTE))
        val shiftStartTime = Timestamp(calendar.time)

        // 设置结束时间
        val endCalendar = Calendar.getInstance()
        endCalendar.time = endTime
        calendar.set(Calendar.HOUR_OF_DAY, endCalendar.get(Calendar.HOUR_OF_DAY))
        calendar.set(Calendar.MINUTE, endCalendar.get(Calendar.MINUTE))
        val shiftEndTime = Timestamp(calendar.time)

        val shift = Shift(
            employeeId = employeeId,
            startTime = shiftStartTime,
            endTime = shiftEndTime,
            status = "Incomplete"
        )

        db.collection("shifts").add(shift)
            .addOnSuccessListener {
                Log.d("MyApp", "Shift assigned successfully: $shift")
            }
            .addOnFailureListener { e ->
                Log.e("MyApp", "Error assigning shift", e)
            }
    }
}

