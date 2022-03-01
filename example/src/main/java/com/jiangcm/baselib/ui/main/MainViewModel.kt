package com.jiangcm.baselib.ui.main

import androidx.lifecycle.MutableLiveData
import com.jiangcm.common.base.BaseViewModel
import com.jiangcm.common.bean.DemoResponse

class TestViewModel : BaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 0
    }

    private val detailRepository by lazy { MainRepository() }

    val retResponse = MutableLiveData<DemoResponse>()
    val error = MutableLiveData<Boolean>()
    val imgPath = MutableLiveData<String>()

    fun refreshProjectList() {
        launchDataCheck(
            block = {
                detailRepository.getProjectList(INITIAL_PAGE)
            },
            resultState = retResponse,
            isShowDialog = true
        )
    }
}