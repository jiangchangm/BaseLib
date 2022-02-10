package com.jiangcm.base.api.config

import com.jiangcm.base.core.AppManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * @author: jcm
 * @email: jiangcm@aplus-it.cn
 * @createTime: 20-6-3
 */
class TokenInterceptor : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val newRequest = oldRequest.newBuilder()
            .header(
                "token", AppManager.instance.token ?: ""
            )
            .header(
                "appName", "jiangcm-example"
            ).build()
        // 新的请求,添加参数
        return chain.proceed(newRequest)
    }

}