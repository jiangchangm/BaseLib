package com.jiangcm.base.base.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jiangcm.base.callback.livedata.event.EventLiveData
import com.jiangcm.base.network.*
import kotlinx.coroutines.*

typealias Block<T> = suspend (CoroutineScope) -> T

open class BaseViewModel : ViewModel() {

    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    inner class UiLoadingChange {
        //显示加载框
        val showDialog by lazy { EventLiveData<String>() }

        //隐藏
        val dismissDialog by lazy { EventLiveData<Boolean>() }
    }

    val loginStatusInvalid: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @param resultState 请求回调的ResultState数据
     * @param showErrorToast 异常信息是否吐司
     * @param isShowDialog 是否显示加载框
     * @param loadingMessage 是否显示加载框
     * @return Job
     */
    protected fun <T> launchData(
        block: suspend () -> BaseResponse<T>,
        resultState: MutableLiveData<T>,
        isShowDialog: Boolean = false,
        showErrorToast: Boolean = true,
        loadingMessage: String = "loading..."
    ): Job {
        return viewModelScope.launch {
            runCatching {
                if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
                //请求体
                block()
            }.onSuccess { result ->
                loadingChange.dismissDialog.postValue(false)
                resultState.value = result.getResponseData()
            }.onFailure {throwable->
                loadingChange.dismissDialog.postValue(false)
                onError(throwable as Exception, showErrorToast)
            }
        }
    }

    protected fun <T> launchDataCheck(
        block: suspend () -> BaseResponse<T>,
        resultState: MutableLiveData<T>,
        isShowDialog: Boolean = false,
        showErrorToast: Boolean = true,
        loadingMessage: String = "loading..."
    ): Job {
        return viewModelScope.launch {
            runCatching {
                if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
                //请求体
                block()
            }.onSuccess { result ->
                loadingChange.dismissDialog.postValue(false)
                runCatching {
                    //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                    executeResponse(result) {
                        resultState.value = result.getResponseData()
                    }
                }.onFailure { e ->
                    //失败回调
                    onError(e as Exception, showErrorToast)
                }
            }.onFailure {throwable->
                loadingChange.dismissDialog.postValue(false)
                onError(throwable as Exception, showErrorToast)
            }
        }
    }

    /**
     * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
     */
    private suspend fun <T> executeResponse(
        response: BaseResponse<T>,
        success: suspend CoroutineScope.(T) -> Unit
    ) {
        coroutineScope {
            if (response.isSuccess()) success(response.getResponseData())
            else throw ApiException(
               -1,
                "参数解析失败"
            )
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
    open fun onError(e: Exception, showErrorToast: Boolean) {
    }
}