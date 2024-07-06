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
        fetchEmployeeProfile()
    }

    fun fetchEmployeeProfile() {
        viewModelScope.launch {
            try {
                db.collection("employees")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val employees = mutableListOf<Employee>()
                        for (document in querySnapshot) {
                            val employee = document.toObject(Employee::class.java)
                            employees.add(employee)
                        }
                        if (employees.isNotEmpty()) {
                            _employee.value = employees[0]
                        } else {
                            _employee.value = null
                        }
                    }
                    .addOnFailureListener { exception ->
                        _employee.value = null
                        println("Fetch employees failed: ${exception.message}")
                    }
            } catch (e: Exception) {
                _employee.value = null
                println("Fetch employees exception: ${e.message}")
            }
        }
    }

    fun fetchEmployeeProfileById(employeeId: String) {
        viewModelScope.launch {
            try {
                db.collection("employees").document(employeeId)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            val employee = document.toObject(Employee::class.java)
                            _employee.value = employee
                        } else {
                            _employee.value = null
                        }
                    }
                    .addOnFailureListener { exception ->
                        _employee.value = null
                        println("Fetch employee by ID failed: ${exception.message}")
                    }
            } catch (e: Exception) {
                _employee.value = null
                println("Fetch employee by ID exception: ${e.message}")
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
                    .addOnFailureListener { exception ->
                        println("Update employee failed: ${exception.message}")
                    }
            } catch (e: Exception) {
                println("Update employee exception: ${e.message}")
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
