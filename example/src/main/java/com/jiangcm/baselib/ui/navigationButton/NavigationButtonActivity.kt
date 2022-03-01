package com.jiangcm.baselib.ui.navigationButton

import android.os.Bundle
import android.util.Log
import cc.shinichi.library.ImagePreview
import com.alibaba.android.arouter.facade.annotation.Route
import com.jiangcm.baselib.R
import com.jiangcm.baselib.c.ARouterPath
import com.jiangcm.baselib.databinding.ActivityNavigationButtonBinding
import com.jiangcm.baselib.ui.main.TestViewModel
import com.jiangcm.common.base.BaseActivity

@Route(path = ARouterPath.NavigationButtonAc, name = "底部导航栏测试页面")
class NavigationButtonActivity : BaseActivity<TestViewModel, ActivityNavigationButtonBinding>() {

    private val imgPath: String =
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg9.51tietu.net%2Fpic%2F2019-091110%2Fc1sswqrvwwqc1sswqrvwwq.jpg&refer=http%3A%2F%2Fimg9.51tietu.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648626630&t=e4fba33498f762b48478e40d0484b94c"

    override fun layoutId(): Int = R.layout.activity_navigation_button

    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        isfitSystemWindows =true
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
    }

    override fun initData() {
        super.initData()
        mViewModel.imgPath.value = imgPath
    }

    inner class ProxyClick {
        fun checkImg() {
            ImagePreview.getInstance()
                .setContext(this@NavigationButtonActivity)
                .setImage(imgPath)
                .setIndex(0)
                .setTransitionView(mDatabind.img)
                .setTransitionShareElementName("shared_element_container")
                .start()
        }
    }
}