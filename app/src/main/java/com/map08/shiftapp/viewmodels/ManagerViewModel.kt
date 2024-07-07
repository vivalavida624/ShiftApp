package com.map08.shiftapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.map08.shiftapp.models.Manager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ManagerViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _managerList = MutableStateFlow<List<Manager>>(emptyList())
    val managerList: StateFlow<List<Manager>> = _managerList

    init {
        fetchManagerList()
    }

    private fun fetchManagerList() {
        viewModelScope.launch {
            try {
                db.collection("managers")
                    .get()
                    .addOnSuccessListener { documents ->
                        val managers = documents.mapNotNull { it.toObject(Manager::class.java) }
                        _managerList.value = managers
                    }
                    .addOnFailureListener {
                        // handle failure
                    }
            } catch (e: Exception) {
                // handle exception
            }
        }
    }
}
