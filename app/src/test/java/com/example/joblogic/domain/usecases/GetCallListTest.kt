package com.example.joblogic.domain.usecases

import arrow.core.Either.Left
import arrow.core.Either.Right
import com.example.joblogic.core.Failure
import com.example.joblogic.core.NoParams
import com.example.joblogic.domain.entities.Contact
import com.example.joblogic.domain.repositories.ContactRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCallListTest {
    private lateinit var mockContactRepository: ContactRepository
    private lateinit var getCallList: GetCallList

    @Before
    fun setup() {
        mockContactRepository = mockk()
        getCallList = GetCallList(mockContactRepository)
    }

    @Test
    fun `should get contact list from ContactRepository`() = runBlockingTest {
        //arrange
        val dummyContactList = listOf(Contact(1, "dummyName", "12345678"))
        coEvery { mockContactRepository.getContactList() } returns Right(dummyContactList)

        //act
        val result = getCallList(NoParams)

        //assert
        result shouldBe Right(dummyContactList)
        coVerify { mockContactRepository.getContactList() }
    }

    @Test
    fun `should return ServerFailure when ContactRepository return ApiFailure`() = runBlockingTest {
        //arrange
        coEvery { mockContactRepository.getContactList() } returns Left(Failure.ApiFailure(400))

        //act
        val result = getCallList(NoParams)

        //assert
        result shouldBe Left(Failure.ApiFailure(400))
        coVerify { mockContactRepository.getContactList() }
    }
}