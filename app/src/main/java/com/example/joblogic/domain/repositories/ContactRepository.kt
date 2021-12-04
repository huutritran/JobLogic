package com.example.joblogic.domain.repositories

import arrow.core.Either
import com.example.joblogic.core.Failure
import com.example.joblogic.domain.entities.Contact

interface ContactRepository {
    suspend fun getContactList(): Either<Failure, List<Contact>>
}