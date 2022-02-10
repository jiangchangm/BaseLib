package com.jiangcm.base.api

import okhttp3.RequestBody
import retrofit2.http.*

/**
 * @author: jcm
 * @email: jiangcm@aplus-it.cn
 * @createTime: 20-6-3
 */
object ApiC {
    private const val API_ANDROID = "/app"

    private const val API_GROUP = "/skdy-api-app"

    const val MY_LIST = "$API_GROUP/calender/entrust"

    const val DEMO_GET = "$API_GROUP/tv/childRecipes"

    const val TEST_GET = "/article/listproject/{page}/json"

}

interface Api {

    @POST(ApiC.MY_LIST)
    @Headers(RetrofitClient.BaseHeader)
    suspend fun demoList(@Body body: RequestBody): ApiResult<List<Any>>

    @GET(ApiC.DEMO_GET)
    @Headers(RetrofitClient.BaseHeader)
    suspend fun demoGet(@Query("idn") idn: String): ApiResult<Any>

    @GET(ApiC.TEST_GET)
    @Headers(RetrofitClient.BaseHeader)
    suspend fun getProjectList(@Path("page") page: Int): ApiResult<Any>

}