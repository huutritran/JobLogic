package com.example.joblogic.domain.usecases

import arrow.core.Either
import com.example.joblogic.core.Failure
import com.example.joblogic.core.NoParams
import com.example.joblogic.core.UseCase
import com.example.joblogic.domain.entities.Contact
import com.example.joblogic.domain.repositories.ContactRepository

class GetCallList(
    private val contactRepository: ContactRepository
): UseCase<List<Contact>, NoParams>() {

    override suspend fun invoke(params: NoParams): Either<Failure, List<Contact>> {
        return contactRepository.getContactList()
    }

}