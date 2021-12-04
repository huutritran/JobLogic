package com.example.joblogic.data.datasources.model

import com.example.joblogic.domain.entities.Contact

typealias ContactListResult = List<ContactModel>

fun ContactListResult.toContactEntities(): List<Contact> = map { model ->
    Contact(
        id = model.id,
        name = model.name,
        number = model.number
    )
}

data class ContactModel(
    val id: Int,
    val name: String,
    val number: String
)