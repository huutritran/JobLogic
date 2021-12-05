package com.example.joblogic.data.datasources.model

import com.example.joblogic.domain.entities.Amount
import com.example.joblogic.domain.entities.ProductItem

typealias BuyListResult = List<ProductModel>

fun BuyListResult.toProductEntities(): List<ProductItem> = map {
    ProductItem(
        id = it.id,
        name = it.name,
        price = Amount(it.price),
        quantity = it.quantity,
        type = it.type
    )
}
