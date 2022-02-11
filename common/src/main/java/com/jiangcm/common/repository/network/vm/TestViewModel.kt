package com.jiangcm.common.repository.network.vm

import androidx.lifecycle.MutableLiveData
import com.jiangcm.common.api.ApiService
import com.jiangcm.common.base.AppBaseViewModel

class TestViewModel : AppBaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 0
    }

    val retResponse = MutableLiveData<Any?>()
    val retError = MutableLiveData<Any?>()

    fun refreshProjectList() {
        launch(
            block = {
                val pagination = ApiService.getProjectList(INITIAL_PAGE)
                retResponse.value = pagination
            },
            error = {
                retError.value = it
            }
        )
    }
}