package com.nmt.film4life.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nmt.film4life.BuildConfig
import com.nmt.film4life.api.AppApi
import com.nmt.film4life.application.MainApplication
import com.nmt.film4life.config.Config
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
val singleTonModule = module {

    single { GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create() }

}
val retrofitModule= module {

    fun provideAppApi(gson: Gson, okHttpClient: OkHttpClient): AppApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_TMDB_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build().create(AppApi::class.java)
    }


    fun provideOkHttpClient(app: MainApplication): OkHttpClient {
        val httpInterceptor = HttpLoggingInterceptor()
        httpInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .cache(null)
            .connectTimeout(Config.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(Config.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(Config.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(httpInterceptor)
            .build()
    }


    single { provideOkHttpClient(androidApplication() as MainApplication) }
    single { provideAppApi(get(),get())}
}