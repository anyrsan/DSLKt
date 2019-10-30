package com.any.org.dslnetlibrary


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @author any
 * @date 2017/11/30
 */
abstract class RetrofitBaseApi(private val baseUrl: String) {

    private val mRetrofit: Retrofit

    abstract fun rLog(message: String)

    init {
        mRetrofit = initRetrofit()
    }

    private fun configOkHttpClient(): OkHttpClient {
        val  interceptor = HttpLoggingInterceptor(object :HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                rLog(message)
            }
        })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
        return builder.build()
    }

    /**
     * 获取Retrofit对象
     *
     * @return
     */
    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(configOkHttpClient())
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    /**
     * 创建ServiceApi
     *
     * @param service
     * @param <T>
     * @return
    </T> */
    fun <T> create(service: Class<T>?): T {
        if (service == null) {
            throw RuntimeException("Api service_agent is null!")
        }
        return mRetrofit.create(service)
    }


    companion object {
        /**
         * 连接超时时长x秒
         */
        const val CONNECT_TIME_OUT = 30
        /**
         * 读数据超时时长x秒
         */
        const val READ_TIME_OUT = 30
        /**
         * 写数据接超时时长x秒
         */
        const val WRITE_TIME_OUT = 30
    }

}
