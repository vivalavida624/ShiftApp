package com.map08.shiftapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.map08.shiftapp.models.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ManagerViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _employeeList = MutableStateFlow<List<Employee>>(emptyList())
    val employeeList: StateFlow<List<Employee>> = _employeeList

    init {
        fetchEmployees()
    }

    private fun fetchEmployees() {
        viewModelScope.launch {
            try {
                db.collection("employees")
                    .get()
                    .addOnSuccessListener { documents ->
                        val employees = documents.mapNotNull { it.toObject(Employee::class.java) }
                        _employeeList.value = employees
                    }
                    .addOnFailureListener {
                        _employeeList.value = emptyList()
                    }
            } catch (e: Exception) {
                _employeeList.value = emptyList()
            }
        }
    }
}
