package com.sample.com.exam.Dependency.Module

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import com.sample.com.exam.BuildConfig
import com.sample.com.exam.Model.Repository.Local.Interface.IDeliveryAPI
import com.sample.com.exam.Shared.ApiHelper.ApiResponseInterceptor

@Module
class ApiModule {

    private val BASE_URL = BuildConfig.API_DOMAIN
    private val TIMEOUT_SEC = BuildConfig.TIMEOUT_SEC

    @Singleton
    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {

        try {
            val trustAllCerts = arrayOf<TrustManager>(
                    object: X509TrustManager {
                        override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {

                        }

                        override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {

                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }

                    })

            val sslContext = SSLContext.getInstance("SSL")

            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { p0, p1 ->
                true
            }

            builder.addInterceptor(ApiResponseInterceptor())
            builder.connectTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)

            return builder.build()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }

    @Singleton
    @Provides
    fun provideDeliveryAPI(retrofit: Retrofit): IDeliveryAPI {
        return retrofit.create(IDeliveryAPI::class.java)
    }


}