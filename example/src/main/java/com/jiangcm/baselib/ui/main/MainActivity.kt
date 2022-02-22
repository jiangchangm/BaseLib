package com.jiangcm.baselib.ui.main

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jiangcm.common.core.AppManager
import com.jiangcm.baselib.databinding.ActivityMainBinding
import com.jiangcm.baselib.R
import com.jiangcm.baselib.c.ARouterPath
import com.jiangcm.common.base.BaseActivity

@Route(path = ARouterPath.MainAc, name = "项目主页Activity")
class MainActivity : BaseActivity<TestViewModel, ActivityMainBinding>() {


    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java

    override fun layoutId(): Int =R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mDatabind.vm = mViewModel
    }

    override fun initData() {
        mViewModel.refreshProjectList()
    }

    override fun onBackPressed() {
        AppManager.instance.doubleBackToExit()
    }

}