package com.jiangcm.common.base

import com.google.gson.JsonSyntaxException
import com.jiangcm.base.R
import com.jiangcm.base.base.vm.BaseViewModel
import com.jiangcm.base.ext.Terror
import com.jiangcm.base.ext.showToast
import com.jiangcm.base.network.ApiException
import com.jiangcm.common.core.AppManager
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

open class AppBaseViewModel : BaseViewModel() {
    /**
     * 统一处理错误
     * @param e 异常
     * @param showErrorToast 是否显示错误吐司
     */
    override fun onError(e: Exception, showErrorToast: Boolean) {
        when (e) {
            is ApiException -> {
                when (e.code) {
                    401 -> {
                        // 登录失效，清除用户信息、清除cookie/token
                        loginStatusInvalid.value = true
                    }
                    // 其他错误
                    else -> if (showErrorToast) AppManager.instance.Terror(e.message)
                }
            }
            is CancellationException->
                if (showErrorToast) AppManager.instance.showToast(R.string.network_work_failed)
                // 网络请求失败
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException,
            is HttpException,
            is SSLHandshakeException ->
                if (showErrorToast) AppManager.instance.showToast(R.string.network_request_failed)
            // 数据解析错误
            is JsonSyntaxException ->
                if (showErrorToast) AppManager.instance.showToast(R.string.api_data_parse_error)
            // 其他错误
            else ->
                if (showErrorToast) AppManager.instance.Terror(e.message ?: return)
        }
    }
}