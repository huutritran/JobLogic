package com.example.joblogic.core

sealed class Failure {
    object ServerFailure : Failure()
    object NetworkFailure : Failure()
}