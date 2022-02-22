package com.jiangcm.baselib.ui.main

import androidx.lifecycle.MutableLiveData
import com.jiangcm.common.base.AppBaseViewModel
import com.jiangcm.common.bean.DemoResponse

class TestViewModel : AppBaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 0
    }

    private val detailRepository by lazy { MainRepository() }

    val retResponse = MutableLiveData<DemoResponse>()

    fun refreshProjectList() {
        launchDataCheck(
            block = {
                detailRepository.getProjectList(INITIAL_PAGE)
            },
            resultState = retResponse, isShowDialog = true
        )
    }
}