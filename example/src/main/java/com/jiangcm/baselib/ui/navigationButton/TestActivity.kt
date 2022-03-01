package com.jiangcm.baselib.ui.navigationButton

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jiangcm.baselib.R
import com.jiangcm.baselib.c.ARouterPath
import com.jiangcm.baselib.databinding.ActivityTestBinding
import com.jiangcm.baselib.databinding.ActivityWebBinding
import com.jiangcm.baselib.ui.main.TestViewModel
import com.jiangcm.common.base.BaseActivity

@Route(path = ARouterPath.TestAc, name = "测试网页")
class TestActivity : BaseActivity<TestViewModel, ActivityTestBinding>() {

    private val imgPath: String =
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg9.51tietu.net%2Fpic%2F2019-091110%2Fc1sswqrvwwqc1sswqrvwwq.jpg&refer=http%3A%2F%2Fimg9.51tietu.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648626630&t=e4fba33498f762b48478e40d0484b94c"


    override fun layoutId(): Int = R.layout.activity_test

    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        isfitSystemWindows =true
        mDatabind.vm = mViewModel
    }

    override fun initData() {
        super.initData()
        mViewModel.imgPath.value = imgPath
    }
}