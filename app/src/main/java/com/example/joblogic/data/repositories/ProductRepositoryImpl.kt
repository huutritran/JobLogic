package com.example.joblogic.data.repositories

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import com.example.joblogic.core.Failure
import com.example.joblogic.data.datasources.local.ProductDAO
import com.example.joblogic.data.datasources.model.ProductModel
import com.example.joblogic.data.datasources.model.toProductEntities
import com.example.joblogic.data.datasources.remote.JobLogicRemoteDataSource
import com.example.joblogic.data.util.withHandlingException
import com.example.joblogic.domain.entities.ItemToBuy
import com.example.joblogic.domain.repositories.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ProductRepositoryImpl constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val remoteDataSource: JobLogicRemoteDataSource,
    private val productDAO: ProductDAO
) : ProductRepository {
    override suspend fun getBuyList(): Either<Failure, List<ItemToBuy>> =
        withHandlingException(ioDispatcher) {
            return@withHandlingException remoteDataSource.getBuyList().toProductEntities()
        }

    override suspend fun getSellList(): Either<Failure, List<ItemToBuy>> =
        withContext(ioDispatcher) {
            try {
                var result = productDAO.getSellList()
                if (result.isEmpty()) {
                    result = prePopulateData()
                }
                Right(result.toProductEntities())
            } catch (e: Exception) {
                Left(Failure.OtherFailure)
            }
        }

    private fun prePopulateData(): List<ProductModel> {
        val products = listOf(
            ProductModel(
                1,
                "iPhone X",
                150000,
                1,
                2
            ),
            ProductModel(
                2,
                "TV",
                38000,
                2,
                2
            ),
            ProductModel(
                3,
                "Table",
                12000,
                1,
                2
            )
        )
        productDAO.addAll(products)
        return products
    }
}