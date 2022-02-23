package com.jiangcm.baselib.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.jiangcm.base.ext.onClickStart
import com.jiangcm.common.core.AppManager
import com.jiangcm.baselib.databinding.ActivityMainBinding
import com.jiangcm.baselib.R
import com.jiangcm.baselib.c.ARouterPath
import com.jiangcm.baselib.c.Const
import com.jiangcm.baselib.ui.adapter.HomeAdapter
import com.jiangcm.baselib.ui.proxy.HomeClick
import com.jiangcm.common.base.BaseActivity
import com.jiangcm.common.ext.init
import kotlinx.coroutines.delay

@Route(path = ARouterPath.MainAc, name = "项目主页Activity")
class MainActivity : BaseActivity<TestViewModel, ActivityMainBinding>() {

    private val homeAdapter by lazy { HomeAdapter() }

    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java

    override fun layoutId(): Int =R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        isfitSystemWindows = true
        mDatabind.baseRy.init(LinearLayoutManager(this),homeAdapter)
        mDatabind.click = HomeClick()
    }

    override fun initData() {
        super.initData()
        homeAdapter.setList(Const.homeItemData)
    }

    override fun onBackPressed() {
        AppManager.instance.doubleBackToExit()
    }

}