package com.jiangcm.baselib.main

import androidx.lifecycle.MutableLiveData
import com.jiangcm.common.base.AppBaseViewModel

class TestViewModel : AppBaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 0
    }
    private val detailRepository by lazy { MainRepository() }

    val retResponse = MutableLiveData<Any?>()
    val retError = MutableLiveData<Any?>()

    fun refreshProjectList() {
        launch(
            block = {
                val pagination = detailRepository.getProjectList(INITIAL_PAGE)
                retResponse.value = pagination
            },
            error = {
                retError.value = it
            }
        )
    }
}