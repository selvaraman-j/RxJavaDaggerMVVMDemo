package demo.selva.com.rxjavadaggermvvmdemo.di

import android.app.Application
import dagger.Module
import dagger.Provides
import demo.selva.com.rxjavadaggermvvmdemo.BuildConfig
import demo.selva.com.rxjavadaggermvvmdemo.Repository.CurrencyAPIService
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class WebServiceModule {

    private val cacheSize: Long = 10 * 1024 * 1024//10MiB

    @Provides
    @Singleton
    fun provideOkHttpClientCache(application: Application): Cache {
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        builder.cache(cache)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideGSON(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRxJava2AdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
                        , gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyApiService(retrofit: Retrofit): CurrencyAPIService {
        return retrofit.create(CurrencyAPIService::class.java)
    }
}