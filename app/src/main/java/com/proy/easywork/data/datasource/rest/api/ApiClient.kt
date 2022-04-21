package com.proy.easywork.data.datasource.rest.api

import com.proy.easywork.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private var apiClient: IApiClient? = null

    fun getServer(): String {
        return BuildConfig.ServerUrl
    }

    fun getApiClient(): IApiClient? {
        if (apiClient == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(getServer())
                .client(okClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            apiClient = retrofit.create(IApiClient::class.java)
        }
        return apiClient
    }


    private fun okClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.MINUTES)
            .build()
    }
}