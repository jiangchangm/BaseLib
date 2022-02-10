package com.jiangcm.baselib.vm

import com.jiangcm.common.base.BaseViewModel

class TestViewModel : BaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 0
    }

    var str: String? = null
}