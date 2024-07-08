package com.map08.shiftapp.models

import com.google.firebase.Timestamp

data class Shift(
    val employeeId: String = "",
    val startTime: Timestamp = Timestamp.now(),
    val endTime: Timestamp = Timestamp.now(),
    val status: String = ""
)
