package com.testforme.newregistr.retrofit

import com.google.gson.GsonBuilder
import com.testforme.newregistr.MainActivity.Companion.MAIN_SITE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitApi {

    private var BASE_URL = MAIN_SITE_URL+"appstack/"
    private var retrofit: Retrofit? = null

    fun getInstance(): Retrofit {
        if (retrofit == null) {
            val gson = GsonBuilder()
                    .setLenient()
                    .create()

            val logging = HttpLoggingInterceptor()
            // set your desired log level
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder().apply {
                connectTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)
                addInterceptor(logging)
            }

            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build()
        }
        return retrofit!!
    }
}