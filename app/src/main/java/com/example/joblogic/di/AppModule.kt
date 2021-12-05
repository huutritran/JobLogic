package com.example.joblogic.di

import com.example.joblogic.BuildConfig
import com.example.joblogic.data.datasources.remote.JobLogicRemoteDataSource
import com.example.joblogic.data.repositories.ContactRepositoryImpl
import com.example.joblogic.data.repositories.ProductRepositoryImpl
import com.example.joblogic.domain.repositories.ContactRepository
import com.example.joblogic.domain.repositories.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @MainDispatcher
    @Provides
    @Singleton
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @IODispatcher
    @Provides
    @Singleton
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideJobLogicRemoteDataSource(retrofit: Retrofit): JobLogicRemoteDataSource {
        return retrofit.create(JobLogicRemoteDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideContactRepository(contactRepositoryImpl: ContactRepositoryImpl): ContactRepository {
        return contactRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository {
        return productRepositoryImpl
    }
}