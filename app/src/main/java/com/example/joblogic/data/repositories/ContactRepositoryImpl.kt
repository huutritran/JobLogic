package com.example.joblogic.data.repositories

import arrow.core.Either
import arrow.core.Either.Right
import arrow.core.Either.Left
import com.example.joblogic.core.Failure
import com.example.joblogic.data.datasources.model.toContactEntities
import com.example.joblogic.data.datasources.remote.JobLogicRemoteDataSource
import com.example.joblogic.domain.entities.Contact
import com.example.joblogic.domain.repositories.ContactRepository
import retrofit2.HttpException
import java.io.IOException

class ContactRepositoryImpl(
    private val jobLogicRemoteDataSource: JobLogicRemoteDataSource
): ContactRepository {
    override suspend fun getContactList(): Either<Failure, List<Contact>> {
        return try {
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