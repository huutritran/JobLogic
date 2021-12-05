package com.example.joblogic.data.repositories

import arrow.core.Either
import arrow.core.Either.Right
import arrow.core.Either.Left
import com.example.joblogic.core.Failure
import com.example.joblogic.data.datasources.model.toContactEntities
import com.example.joblogic.data.datasources.remote.JobLogicRemoteDataSource
import com.example.joblogic.di.IODispatcher
import com.example.joblogic.domain.entities.Contact
import com.example.joblogic.domain.repositories.ContactRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    @IODispatcher val ioDispatcher: CoroutineDispatcher,
    private val jobLogicRemoteDataSource: JobLogicRemoteDataSource
) : ContactRepository {
    override suspend fun getContactList(): Either<Failure, List<Contact>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val listContact = jobLogicRemoteDataSource
                    .getContactList()
                    .toContactEntities()

                Right(listContact)
            } catch (e: HttpException) {
                Left(Failure.ApiFailure(e.code()))
            } catch (e: IOException) {
                Left(Failure.NetworkFailure)
            }
        }
}