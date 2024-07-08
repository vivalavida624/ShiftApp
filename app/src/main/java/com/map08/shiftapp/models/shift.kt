package com.map08.shiftapp.models

import java.util.Date

data class Shift(
    val employeeId: String = "",
    val startTime: Date = Date(),
    val endTime: Date = Date(),
    val status: String = ""
)
