package com.brilliantzhao.baselibrary.module

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.brilliantzhao.baselibrary.api.ExampleApi
import com.brilliantzhao.baselibrary.api.getRequestHead
import com.brilliantzhao.baselibrary.constant.DEFAULT_CACHE_FILE_PATH
import com.brilliantzhao.baselibrary.util.getAppIsLogShowLog
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import java.io.File
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.*

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
@Module(includes = arrayOf(AppModule::class))
class ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: HttpUrl, client: OkHttpClient, gson: Gson) =
            Retrofit.Builder()
                    .client(client)
                    // Retrofit2 的baseUlr 必须以 /（斜线） 结束，不然会抛出一个IllegalArgumentExceptiond 的异常
                    // http://192.168.0.1:9001?id=value 这样做为baseUrl亦可以，?id=value会在请求时被丢掉
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build()

    @Provides
    fun provideBaseUrl(context: Context) = HttpUrl.parse(getRequestHead(context))

    @Provides
    fun provideOkhttp(context: Context, interceptor: HttpLoggingInterceptor): OkHttpClient {
        val cacheSize = 1024 * 1024 * 100L // 最大100M
        val cacheDir = File(DEFAULT_CACHE_FILE_PATH, "OkHttpCache")
        val cache = Cache(cacheDir, cacheSize)
        return OkHttpClient.Builder()
                // 证书
                .sslSocketFactory(provideSSLSocketFactory())
                .hostnameVerifier(HostnameVerifier { hostname, session -> true })
                // 超时
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                //设置缓存
                .addNetworkInterceptor(OfflineCacheControlInterceptor())
                .addNetworkInterceptor(StethoInterceptor()) // google stetho网络监控
                .retryOnConnectionFailure(true) //失败重连
                .cache(cache) //设置Cache目录
                .addInterceptor(interceptor) //打印日志
                .build()
    }

    @Provides
    fun provideInterceptor(context: Context): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { msg ->
            LogUtils.d(msg)
        }
        if (getAppIsLogShowLog(context)) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Provides
    fun provideGson() = GsonBuilder().create()

    @Provides
    fun provideApi(retrofit: Retrofit) = retrofit.create(ExampleApi::class.java)

    /**
     *
     */
    @Provides
    fun provideSSLSocketFactory(): SSLSocketFactory {
        //证书
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })

        // Install the all-trusting trust manager
        val sslContext: SSLContext
        sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        return sslSocketFactory
    }
}

