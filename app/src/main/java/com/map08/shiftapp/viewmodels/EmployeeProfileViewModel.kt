package com.map08.shiftapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.map08.shiftapp.models.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmployeeProfileViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _employee = MutableStateFlow<Employee?>(null)
    val employee: StateFlow<Employee?> = _employee

    init {
        fetchEmployeeProfile()
    }

    fun fetchEmployeeProfile() { // Change to public
        viewModelScope.launch {
            try {
                db.collection("employees").document("cHgco7EfjuoPGGPZcVmh") // 使用实际文档ID
                    .get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            val employee = document.toObject(Employee::class.java)
                            _employee.value = employee
                        } else {
                            _employee.value = null
                        }
                    }
                    .addOnFailureListener {
                        _employee.value = null
                    }
            } catch (e: Exception) {
                _employee.value = null
            }
        }
    }

    fun updateEmployeeProfile(updatedEmployee: Employee) {
        db.collection("employees").document("cHgco7EfjuoPGGPZcVmh")
            .set(updatedEmployee)
            .addOnSuccessListener {
                fetchEmployeeProfile()
            }
            .addOnFailureListener {
                // Handle failure
            }
    }
}
