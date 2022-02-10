package com.jiangcm.base.repository.network.vm

import androidx.lifecycle.MutableLiveData
import com.jiangcm.base.api.ApiService
import com.jiangcm.base.base.AppBaseViewModel

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