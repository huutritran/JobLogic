package com.example.joblogic.data.datasources.remote

import com.example.joblogic.data.datasources.model.ContactListResult
import retrofit2.http.GET

interface JobLogicRemoteDataSource {
    @GET("call")
    suspend fun getContactList(): ContactListResult
}