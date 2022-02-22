package com.jiangcm.baselib.ui.guideView

import android.annotation.SuppressLint
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jiangcm.base.ext.Tnormal
import com.jiangcm.base.widght.guideview.Component
import com.jiangcm.base.widght.guideview.GuideBuilder
import com.jiangcm.baselib.R
import com.jiangcm.baselib.c.ARouterPath
import com.jiangcm.baselib.databinding.ActivityGudieSampleBinding
import com.jiangcm.baselib.ui.guideView.component.LottieComponent
import com.jiangcm.baselib.ui.guideView.component.MutiComponent
import com.jiangcm.baselib.ui.guideView.component.SimpleComponent
import com.jiangcm.baselib.ui.main.TestViewModel
import com.jiangcm.common.base.BaseActivity

@Route(path = ARouterPath.GuideSampleAc, name = "遮罩层示例Activity")
class GuideSampleActivity : BaseActivity<TestViewModel, ActivityGudieSampleBinding>() {

    override fun layoutId(): Int = R.layout.activity_gudie_sample

    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mDatabind.vm = mViewModel
        mDatabind.baseCy.post {
            showGuideView()
        }
    }

    private fun showGuideView() {
        GuideBuilder()
            .setTargetView(mDatabind.baseCy)
            .setAlpha(150)
            .setHighTargetGraphStyle(Component.UNROUNDRECT)
            .addComponent(SimpleComponent().setOnListener {
                Tnormal("哈哈")
                GuideBuilder.dismiss()
            })
            .setOnVisibilityChangedListener(object : GuideBuilder.OnVisibilityChangedListener {
                override fun onShown() {}
                override fun onDismiss() {
                    showGuideView2()
                }
            })
            .createGuide()
            .show(this@GuideSampleActivity, null, true)
    }

    fun showGuideView2() {
        GuideBuilder()
            .setTargetView(mDatabind.text)
            .setAlpha(150)
            .setHighTargetGraphStyle(Component.CIRCLE)
            .setOnVisibilityChangedListener(object : GuideBuilder.OnVisibilityChangedListener {
                override fun onShown() {}
                override fun onDismiss() {
                    showGuideView3()
                }
            })
            .addComponent(MutiComponent())
            .createGuide()
            .show(this@GuideSampleActivity)
    }

    @SuppressLint("ResourceType")
    fun showGuideView3() {
        GuideBuilder()
            .setTargetView(mDatabind.textView)
            .setAlpha(150)
            .setHighTargetCorner(20)
            .setHighTargetPadding(10)
            .setExitAnimationId(android.R.anim.fade_out)
            .addComponent(LottieComponent())
            .createGuide()
            .setShouldCheckLocInWindow(false)
            .show(this@GuideSampleActivity)
    }
}