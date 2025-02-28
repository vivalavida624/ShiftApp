package com.map08.shiftapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
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
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            fetchEmployeeProfile(userId)
        }
    }

    fun fetchEmployeeProfile(userId: String) {
        viewModelScope.launch {
            try {
                db.collection("employees").document(userId)
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

    fun updateEmployeeProfile(employee: Employee) {
        viewModelScope.launch {
            try {
                db.collection("employees").document(employee.id)
                    .set(employee)
                    .addOnSuccessListener {
                        _employee.value = employee
                    }
                    .addOnFailureListener {
                        // handle failure
                    }
            } catch (e: Exception) {
                // handle exception
            }
        }
    }

    fun createEmployeeProfile(employee: Employee) {
        viewModelScope.launch {
            try {
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId != null) {
                    employee.id = userId
                    db.collection("employees").document(userId)
                        .set(employee)
                        .addOnSuccessListener {
                            _employee.value = employee
                        }
                        .addOnFailureListener { exception ->
                            println("Create employee failed: ${exception.message}")
                        }
                }
            } catch (e: Exception) {
                println("Create employee exception: ${e.message}")
            }
        }
    }
}
