package com.example.joblogic.domain.entities

data class Amount(
    val value: Long,
    val decimalShift: Int = 0
)