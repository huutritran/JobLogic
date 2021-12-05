package com.example.joblogic.data.datasources.remote

import com.example.joblogic.data.datasources.model.BuyListResult
import com.example.joblogic.data.datasources.model.ContactListResult
import retrofit2.http.GET

interface JobLogicRemoteDataSource {
    @GET("imkhan334/demo-1/call")
    suspend fun getContactList(): ContactListResult

    @GET("imkhan334/demo-1/buy")
    suspend fun getBuyList(): BuyListResult
}