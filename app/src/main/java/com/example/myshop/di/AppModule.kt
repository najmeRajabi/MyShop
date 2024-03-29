package com.example.myshop.di

import com.example.myshop.data.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()


@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    @Singleton
    internal fun apiService (moshi: Moshi, retrofit: Retrofit): ApiService {
        val loginApiService = retrofit.create(ApiService::class.java)
        return loginApiService

    }

    @Provides
    @Singleton
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun getRetrofit(moshi: Moshi): Retrofit {
        return  Retrofit
            .Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("http://woocommerce.maktabsharif.ir/wp-json/wc/v3/")
            .client(client)
            .build()
    }
}