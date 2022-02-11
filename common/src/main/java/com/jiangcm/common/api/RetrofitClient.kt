package com.jiangcm.common.api

import com.jiangcm.common.api.config.LoggerInterceptor
import com.jiangcm.common.api.config.TokenInterceptor
import com.jiangcm.common.api.config.TrustAllCerts
import com.jiangcm.common.utils.PropertyUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    const val BaseHeader = "Content-type:application/json;charset=UTF-8"

    /**OkhttpClient*/
    private val okHttpClient = OkHttpClient.Builder()
        .followRedirects(false)
        .followSslRedirects(false)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .sslSocketFactory(TrustAllCerts.createSSLSocketFactory())
        .hostnameVerifier(TrustAllCerts.TrustAllHostnameVerifier())
        .addInterceptor(TokenInterceptor())
        .addInterceptor(LoggerInterceptor("okhttp")).build()

    /**Retrofit*/
    fun getService(): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(PropertyUtils.getPropertiesByName(PropertyUtils.BASE_API_URL))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}