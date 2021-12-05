package com.example.joblogic.data.repositories

import arrow.core.Either
import com.example.joblogic.core.Failure
import com.example.joblogic.data.datasources.model.toContactEntities
import com.example.joblogic.data.datasources.remote.JobLogicRemoteDataSource
import com.example.joblogic.data.util.withHandlingException
import com.example.joblogic.di.IODispatcher
import com.example.joblogic.domain.entities.Contact
import com.example.joblogic.domain.repositories.ContactRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    @IODispatcher val ioDispatcher: CoroutineDispatcher,
    private val jobLogicRemoteDataSource: JobLogicRemoteDataSource
) : ContactRepository {
    override suspend fun getContactList(): Either<Failure, List<Contact>> =
        withHandlingException(ioDispatcher) {
            return@withHandlingException jobLogicRemoteDataSource
                .getContactList()
                .toContactEntities()
        }

}