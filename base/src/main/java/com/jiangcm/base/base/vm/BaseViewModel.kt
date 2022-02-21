package com.jiangcm.base.base.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jiangcm.base.callback.livedata.event.EventLiveData
import com.jiangcm.base.network.BaseResponse
import com.jiangcm.base.network.ResultState
import com.jiangcm.base.network.paresException
import com.jiangcm.base.network.paresResult
import kotlinx.coroutines.*

typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend (Exception) -> Unit

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
     * @param isShowDialog 是否显示加载框
     * @param loadingMessage 是否显示加载框
     * @return Job
     */
    protected fun<T> launch(
        block: suspend () -> BaseResponse<T>,
        resultState: MutableLiveData<ResultState<T>>,
        isShowDialog: Boolean = false,
        loadingMessage: String = "loading..."
    ): Job {
        return viewModelScope.launch {
            runCatching {
                if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
                //请求体
                block()
            }.onSuccess {
                loadingChange.dismissDialog.postValue(false)
                resultState.paresResult(it)
            }.onFailure {
                loadingChange.dismissDialog.postValue(false)
                resultState.paresException(it)
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
    open fun onError(e: Exception, showErrorToast: Boolean) {
    }
}