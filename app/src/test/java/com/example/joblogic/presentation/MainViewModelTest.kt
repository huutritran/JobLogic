package com.example.joblogic.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.Either.Right
import com.example.joblogic.core.NoParams
import com.example.joblogic.domain.entities.Amount
import com.example.joblogic.domain.entities.Contact
import com.example.joblogic.domain.entities.ProductItem
import com.example.joblogic.domain.usecases.GetBuyList
import com.example.joblogic.domain.usecases.GetCallList
import com.example.joblogic.domain.usecases.GetSellList
import com.example.joblogic.domain.usecases.JobLogicUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private lateinit var getCallList: GetCallList
    private lateinit var getBuyList: GetBuyList
    private lateinit var getSellList: GetSellList
    private lateinit var jobLogicUseCase: JobLogicUseCase
    private lateinit var viewModel: MainViewModel
    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        getCallList = mockk()
        getBuyList = mockk()
        getSellList = mockk()
        jobLogicUseCase = JobLogicUseCase(getCallList, getBuyList, getSellList)
        viewModel = MainViewModel(jobLogicUseCase)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should show call list`() {
        //arrange
        val dummyList = listOf(Contact(1, "dummy", "12344567"))
        coEvery { getCallList.invoke(NoParams) } returns Right(dummyList)

        //assert
        assertCallActionCorrectly(ListType.CALL) {
            viewModel.showCallList()
        }
        coVerify { getCallList.invoke(NoParams) }
    }

    @Test
    fun `should show buy list`() {
        val dummyList = listOf(ProductItem(1, "dummy", Amount(10), 1, 1))
        coEvery { getBuyList.invoke(NoParams) } returns Right(dummyList)

        assertCallActionCorrectly(ListType.BUY) {
            viewModel.showBuyList()
        }
        coVerify { getBuyList.invoke(NoParams) }
    }

    @Test
    fun `should show sell list`() {
        val dummyList = listOf(ProductItem(1, "dummy", Amount(10), 1, 1))
        coEvery { getSellList.invoke(NoParams) } returns Right(dummyList)

        assertCallActionCorrectly(ListType.SELL) {
            viewModel.showSellList()
        }
        coVerify { getSellList.invoke(NoParams) }
    }

    private fun assertCallActionCorrectly(expectedTypeChange: ListType, call: () -> Unit) {
        //arrange
        val listTypeObserver = mockk<Observer<ListType>> { every { onChanged(any()) } just Runs }
        viewModel.listType.observeForever(listTypeObserver)

        val loadingObserver = mockk<Observer<Boolean>> { every { onChanged(any()) } just Runs }
        viewModel.loading.observeForever(loadingObserver)

        //act
        call()

        //assert
        verify {
            listTypeObserver.onChanged(expectedTypeChange)
        }
        verifySequence {
            loadingObserver.onChanged(true)
            loadingObserver.onChanged(false)
        }
    }
}