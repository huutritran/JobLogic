package com.example.joblogic.core

sealed class Failure {
    data class ApiFailure(val code:Int) : Failure()
    object NetworkFailure : Failure()
}