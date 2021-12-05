package com.example.joblogic.data.repositories

import arrow.core.Either
import com.example.joblogic.core.Failure
import com.example.joblogic.data.datasources.model.toProductEntities
import com.example.joblogic.data.datasources.remote.JobLogicRemoteDataSource
import com.example.joblogic.data.util.withHandlingException
import com.example.joblogic.di.IODispatcher
import com.example.joblogic.domain.entities.ItemToBuy
import com.example.joblogic.domain.repositories.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val remoteDataSource: JobLogicRemoteDataSource
) : ProductRepository {
    override suspend fun getBuyList(): Either<Failure, List<ItemToBuy>> =
        withHandlingException(ioDispatcher) {
            return@withHandlingException remoteDataSource.getBuyList().toProductEntities()
        }

    override suspend fun getSellList(): Either<Failure, List<ItemToBuy>> {
        TODO("Not yet implemented")
    }
}