package com.example.joblogic.presentation.list

import com.example.joblogic.R
import com.example.joblogic.domain.entities.Contact
import com.example.joblogic.domain.entities.ProductItem

sealed class ItemData {
    data class ProductItem(
        val id: Int,
        val name: String,
        val price: String,
        val quantity: String
    ) : ItemData()

    data class ContactItem(
        val id: Int,
        val name: String,
        val number: String
    ) : ItemData()
}

object ItemType {
    const val TYPE_PRODUCT = R.layout.item_product
    const val TYPE_CONTACT = R.layout.item_contact
}

fun Contact.toItemData(): ItemData.ContactItem {
    return ItemData.ContactItem(id, name, number)
}

fun ProductItem.toItemData():ItemData.ProductItem {
    return ItemData.ProductItem(
        id,
        name,
        price = price.value.toString(),
        quantity = quantity.toString(),
    )
}