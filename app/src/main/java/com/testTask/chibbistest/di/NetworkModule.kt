package com.testTask.chibbistest.di


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import com.testTask.chibbistest.BuildConfig
import com.testTask.chibbistest.data.network.AppApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.internal.Util
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

    val networkModule = module {

        val httpLogging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY

        }



        val gson = GsonBuilder()
            .setLenient()
            .serializeNulls()
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .create()

        val okHttpClient =
            OkHttpClient.Builder()

                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(httpLogging)
                .addInterceptor(Interceptor { chain ->
                  //  println("Токен "+authenticationRepository.getTokenKey())
                    val request: Request.Builder = chain.request().newBuilder()
                    return@Interceptor chain.proceed(request.build())
                })
                .protocols(Util.immutableList(Protocol.HTTP_1_1))
                .build()


        val appApi = Retrofit.Builder()

            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .build().create(AppApi::class.java)


        factory<Gson> { gson }
        factory { httpLogging }
        factory<OkHttpClient> { okHttpClient }
        factory<AppApi> { appApi }


    }



