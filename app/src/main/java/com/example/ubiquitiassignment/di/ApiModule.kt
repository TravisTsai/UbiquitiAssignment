package com.example.ubiquitiassignment.di

import com.example.ubiquitiassignment.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val okHttpClient = OkHttpClient().newBuilder()
            .dispatcher(Dispatcher(Executors.newCachedThreadPool()))
            .build()

        return Retrofit.Builder()
            .baseUrl("https://data.epa.gov.tw/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }


}