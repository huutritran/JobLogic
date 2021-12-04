package com.example.joblogic.data.repositories

import arrow.core.Either.Left
import arrow.core.Either.Right
import com.example.joblogic.core.Failure
import com.example.joblogic.data.datasources.model.ContactListResult
import com.example.joblogic.data.datasources.model.ContactModel
import com.example.joblogic.data.datasources.remote.JobLogicRemoteDataSource
import com.example.joblogic.domain.entities.Contact
import com.example.joblogic.domain.repositories.ContactRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

class ContactRepositoryImplTest {
    private lateinit var remoteDataSource: JobLogicRemoteDataSource
    private lateinit var contactRepository: ContactRepository

    @Before
    fun setUp() {
        remoteDataSource = mockk()
        contactRepository = ContactRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `should get contact list from JobLogicRemoteDataSource`() = runBlockingTest {
        //arrange
        val dummyResult = listOf(ContactModel(1, "dummyName", "123456789"))
        coEvery { remoteDataSource.getContactList() } returns dummyResult

        //act
        val result = contactRepository.getContactList()

        //assert
        result shouldBe Right(listOf(Contact(1, "dummyName", "123456789")))
        coVerify { remoteDataSource.getContactList() }
    }

    @Test
    fun `should return ApiFailure when JobLogicRemoteDataSource throw HttpException`() =
        runBlockingTest {
            //arrange
            val response = Response.error<ContactListResult>(400, ResponseBody.create(null, ""))
            coEvery { remoteDataSource.getContactList() } throws HttpException(response)

            //act
            val result = contactRepository.getContactList()

            //assert
            result shouldBe Left(Failure.ApiFailure(400))
            coVerify { remoteDataSource.getContactList() }
        }

    @Test
    fun `should return Network when JobLogicRemoteDataSource throw any IO exception`() =
        runBlockingTest {
            //arrange
            val response = Response.error<ContactListResult>(400, ResponseBody.create(null, ""))
            coEvery { remoteDataSource.getContactList() } throws UnknownHostException("UnknownHostException")

            //act
            val result = contactRepository.getContactList()

            //assert
            result shouldBe Left(Failure.NetworkFailure)
            coVerify { remoteDataSource.getContactList() }
        }
}