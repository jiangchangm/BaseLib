package com.jiangcm.baselib.ui.navigationButton

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jiangcm.baselib.R
import com.jiangcm.baselib.c.ARouterPath
import com.jiangcm.baselib.databinding.ActivityNavigationButtonBinding
import com.jiangcm.baselib.ui.main.TestViewModel
import com.jiangcm.common.base.BaseActivity

@Route(path = ARouterPath.NavigationButtonAc, name = "底部导航栏测试页面")
class NavigationButtonActivity : BaseActivity<TestViewModel, ActivityNavigationButtonBinding>() {

    override fun layoutId(): Int = R.layout.activity_navigation_button

    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        isfitSystemWindows =true
    }


}