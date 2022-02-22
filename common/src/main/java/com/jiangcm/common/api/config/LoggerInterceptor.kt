package com.jiangcm.common.api.config

import android.text.TextUtils
import com.jiangcm.base.log.LogUtils
import okhttp3.*
import okio.Buffer
import java.io.IOException

class LoggerInterceptor(private val tag:String="OkHttpUtils", private val showResponse:Boolean=true) :
    Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        logForRequest(request)
        val response = chain.proceed(request)
        return logForResponse(response)
    }

    private fun logForResponse(response: Response): Response {
        try {
            //===>response log
            LogUtils.logE(tag, "========response'log=======")
            val builder = response.newBuilder()
            val clone = builder.build()
            LogUtils.logE(tag, "url : " + clone.request().url())
            LogUtils.logE(tag, "code : " + clone.code())
            LogUtils.logE(tag, "protocol : " + clone.protocol())
            if (!TextUtils.isEmpty(clone.message())) {
                LogUtils.logE(tag, "message : " + clone.message())
            }
            if (showResponse) {
                var body = clone.body()
                if (body != null) {
                    val mediaType = body.contentType()
                    if (mediaType != null) {
                        LogUtils.logE(tag, "responseBody's contentType : $mediaType")
                        if (isText(mediaType)) {
                            val resp = body.string()
                            LogUtils.logE(tag, "responseBody's content : $resp")
                            body = ResponseBody.create(mediaType, resp)
                            return response.newBuilder().body(body).build()
                        } else {
                            LogUtils.logE(
                                tag,
                                "responseBody's content : " + " maybe [file part] , too large too print , ignored!"
                            )
                        }
                    }
                }
            }
            LogUtils.logE(tag, "========response'log=======end")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    private fun logForRequest(request: Request) {
        try {
            val url = request.url().toString()
            val headers = request.headers()
            LogUtils.logE(tag, "========request'log=======")
            LogUtils.logE(tag, "method : " + request.method())
            LogUtils.logE(tag, "url : $url")
            if (headers.size() > 0) {
                LogUtils.logE(tag, "headers : $headers")
            }
            val requestBody = request.body()
            if (requestBody != null) {
                val mediaType = requestBody.contentType()
                if (mediaType != null) {
                    LogUtils.logE(tag, "requestBody's contentType : $mediaType")
                    if (isText(mediaType)) {
                        LogUtils.logE(tag, "requestBody's content : " + bodyToString(request))
                    } else {
                        LogUtils.logE(
                            tag,
                            "requestBody's content : " + " maybe [file part] , too large too print , ignored!"
                        )
                    }
                }
            }
            LogUtils.logE(tag, "========request'log=======end")
        } catch (e: Exception) {
        }
    }

    private fun isText(mediaType: MediaType): Boolean {
        if ("text" == mediaType.type()) {
            return true
        }
        if ("json" == mediaType.subtype() || "xml" == mediaType.subtype() || "html" == mediaType.subtype() || "webviewhtml" == mediaType.subtype()) {
            return true
        }
        return false
    }

    private fun bodyToString(request: Request): String? {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()?.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: NullPointerException) {
            e.printStackTrace()
            "something error when show requestBody."
        } catch (e: IOException) {
            "something error when show requestBody."
        }
    }


}
