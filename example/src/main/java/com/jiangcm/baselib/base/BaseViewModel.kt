package com.jiangcm.baselib.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonSyntaxException
import com.jiangcm.baselib.R
import com.jiangcm.baselib.core.AppManager
import com.jiangcm.common.ext.Terror
import com.jiangcm.common.ext.showToast
import com.jiangcm.network.api.ApiException
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend (Exception) -> Unit

open class BaseViewModel : ViewModel() {

    val loginStatusInvalid: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @param error 错误时执行
     * @param cancel 取消时只需
     * @param showErrorToast 是否弹出错误吐司
     * @return Job
     */
    protected fun launch(
        block: Block<Unit>,
        error: Error? = null,
        cancel: Cancel? = null,
        showErrorToast: Boolean = true
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e, showErrorToast)
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @return Deferred<T>
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke(this) }
    }

    /**
     * 取消协程
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    /**
     * 统一处理错误
     * @param e 异常
     * @param showErrorToast 是否显示错误吐司
     */
    private fun onError(e: Exception, showErrorToast: Boolean) {
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
                if (showErrorToast)AppManager.instance.Terror(e.message ?: return)
        }
    }
}