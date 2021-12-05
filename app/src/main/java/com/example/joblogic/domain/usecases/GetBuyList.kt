package com.example.joblogic.domain.usecases

import arrow.core.Either
import com.example.joblogic.core.Failure
import com.example.joblogic.core.NoParams
import com.example.joblogic.core.UseCase
import com.example.joblogic.domain.entities.ItemToBuy
import com.example.joblogic.domain.repositories.ProductRepository
import javax.inject.Inject

class GetBuyList @Inject constructor(private val productRepository: ProductRepository) :
    UseCase<List<ItemToBuy>, NoParams>() {

    override suspend fun invoke(params: NoParams): Either<Failure, List<ItemToBuy>> {
        return productRepository.getBuyList()
    }
}