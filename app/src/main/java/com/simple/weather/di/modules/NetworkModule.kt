package com.simple.weather.di.modules

import com.google.gson.GsonBuilder
import com.simple.weather.BuildConfig
import com.simple.weather.remote.network.NetworkService
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofitForNetworkService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    @Singleton
    fun requestInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("appid", "b240ace8918222e1f2f1e6657eff5c4e").build()

            chain.proceed(request.newBuilder().url(url).build())
        }
    }

    @Singleton
    @Provides
    fun okHttpClient(requestInterceptor: Interceptor): OkHttpClient {
        val client = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(logInterceptor)
        }

        client.connectTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10, TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.addInterceptor(requestInterceptor)

        return client.build()
    }
}