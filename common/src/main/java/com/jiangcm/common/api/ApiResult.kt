package com.jiangcm.common.api

import androidx.annotation.Keep
import com.jiangcm.base.network.BaseResponse


@Keep
data class ApiResult<T>(
    var errorCode: Int,
    var httpcode: Int,
    var errorMsg: String,
    var data: T,
) : BaseResponse<T>() {

    override fun isSuccess() = errorCode == 0

    override fun getResponseCode() = errorCode

    override fun getResponseData() = data

    override fun getResponseMsg() = errorMsg

    override fun isHttpReqSuccess(): Boolean = httpcode == 200

}
