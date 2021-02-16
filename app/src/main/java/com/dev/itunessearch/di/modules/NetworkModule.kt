package com.dev.itunessearch.di.modules

import android.content.Context
import com.dev.itunessearch.data.repository.ITunesRepositoryImp
import com.dev.itunessearch.data.source.local.dao.FavoritesItemsDao
import com.dev.itunessearch.data.source.remote.RetrofitService
import com.dev.itunessearch.domain.repository.ITunesRepository
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RetrofitService.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(context: Context): OkHttpClient {
        val cache = Cache(context.cacheDir, (5 * 1024 * 1024).toLong())
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideITunesItemsRepository(
        favoritesItemsDao: FavoritesItemsDao,
        retrofitService: RetrofitService
    ): ITunesRepository {
        return ITunesRepositoryImp(favoritesItemsDao, retrofitService)
    }
}