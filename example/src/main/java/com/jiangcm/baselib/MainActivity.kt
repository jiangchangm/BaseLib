package com.jiangcm.baselib

import com.jiangcm.baselib.vm.TestViewModel
import com.jiangcm.common.base.BaseVmActivity

class MainActivity : BaseVmActivity<TestViewModel>(){

    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java

    override fun layoutRes(): Int = R.layout.activity_main

    override fun initData() {
        super.initData()
        isTranslucentStatus = true
        fitSystemWindows = true
    }

}