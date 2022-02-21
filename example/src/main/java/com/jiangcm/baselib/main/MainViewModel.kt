package com.jiangcm.baselib.main

import androidx.lifecycle.MutableLiveData
import com.jiangcm.base.base.vm.BaseViewModel
import com.jiangcm.base.network.ResultState
import com.jiangcm.common.bean.DemoResponse

class TestViewModel : BaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 0
    }

    private val detailRepository by lazy { MainRepository() }

    val retResponse = MutableLiveData<ResultState<DemoResponse>>()

    fun refreshProjectList() {
        launch(
            block = {
                detailRepository.getProjectList(INITIAL_PAGE)
            },
            resultState = retResponse, isShowDialog = true
        )
    }
}