package com.example.versioncontrolappcomposable.di

import com.example.versioncontrolappcomposable.data.AppConstants
import com.example.versioncontrolappcomposable.data.api.ApiService
import com.example.versioncontrolappcomposable.data.datasource.ApplicationDataSource
import com.example.versioncontrolappcomposable.data.datasource.ApplicationDataSourceImpl
import com.example.versioncontrolappcomposable.data.repository.ApplicationRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val httpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
        }

        httpClient.apply {
            readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        }

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()

        return Retrofit.Builder()
            .baseUrl(AppConstants.APP_BASE_URL)
            .client(httpClient.build())
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }


    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesApplicationDataSource(apiService: ApiService) : ApplicationDataSource {
        return ApplicationDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun providesApplicationRepository(applicationDataSource: ApplicationDataSource) : ApplicationRepository {
        return ApplicationRepository(applicationDataSource)
    }

}