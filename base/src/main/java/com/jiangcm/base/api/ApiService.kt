package com.jiangcm.base.api

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody

object ApiService {

    private val mediaApiService by lazy {
        RetrofitClient.getService().create(Api::class.java)
    }

    private fun getBody(b: Any): RequestBody {
        return RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            Gson().toJson(b)
        )
    }

    suspend fun getProjectList(id:Int): ApiResult<Any> {
        return mediaApiService.getProjectList(id)
    }


}