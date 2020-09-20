package com.example.mertkesgin.footballapp.di

import android.content.Context
import com.example.mertkesgin.footballapp.data.remote.ApiService
import com.example.mertkesgin.footballapp.utils.Constant
import com.example.mertkesgin.footballapp.data.remote.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = Constant.BASE_URL

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideNetworkConnectionInterceptor(@ApplicationContext context: Context) =
        NetworkConnectionInterceptor(
            context
        )

    @Provides
    fun provideOkHttpClient(
        logger: HttpLoggingInterceptor,
        networkConnectionInterceptor: NetworkConnectionInterceptor
    ): OkHttpClient {

        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(logger)
        okHttpClient.addInterceptor(networkConnectionInterceptor)
        val okHttp = okHttpClient.build()
        return okHttp
    }

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String,okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) =
        retrofit.create(ApiService::class.java)
}