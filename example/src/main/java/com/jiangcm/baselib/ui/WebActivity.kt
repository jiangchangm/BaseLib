package com.jiangcm.baselib.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.jiangcm.baselib.R
import com.jiangcm.baselib.c.ARouterPath
import com.jiangcm.baselib.databinding.ActivityWebBinding
import com.jiangcm.baselib.ui.main.TestViewModel
import com.jiangcm.common.base.BaseActivity

@Route(path = ARouterPath.WebAc, name = "web网页")
class WebActivity : BaseActivity<TestViewModel,ActivityWebBinding>() {

    override fun layoutId(): Int = R.layout.activity_web

    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java

}