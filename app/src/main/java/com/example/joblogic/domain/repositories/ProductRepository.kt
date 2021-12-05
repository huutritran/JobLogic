package com.example.joblogic.domain.repositories

import arrow.core.Either
import com.example.joblogic.core.Failure
import com.example.joblogic.domain.entities.ItemToBuy

interface ProductRepository {

    suspend fun getBuyList(): Either<Failure,List<ItemToBuy>>

    suspend fun getSellList(): Either<Failure,List<ItemToBuy>>

}