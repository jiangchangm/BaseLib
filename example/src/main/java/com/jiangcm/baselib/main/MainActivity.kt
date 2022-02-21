package com.jiangcm.baselib.main

import android.annotation.SuppressLint
import com.jiangcm.common.core.AppManager
import com.jiangcm.baselib.databinding.ActivityMainBinding
import com.jiangcm.base.base.BaseVmActivity
import com.jiangcm.base.ext.Terror
import com.jiangcm.base.ext.Tnormal
import com.jiangcm.base.widght.guideview.Component
import com.jiangcm.base.widght.guideview.GuideBuilder
import com.jiangcm.baselib.R
import com.jiangcm.baselib.guideView.component.LottieComponent
import com.jiangcm.baselib.guideView.component.MutiComponent
import com.jiangcm.baselib.guideView.component.SimpleComponent

class MainActivity : BaseVmActivity<TestViewModel>() {


    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java

    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)

    override fun initView() {
        binding.vm = mViewModel
        binding.baseCy.post {
            showGuideView()
        }
    }

    override fun initData() {
//        showProgress(cancel = false)
//        mViewModel.refreshProjectList()
    }

    override fun observe() {
        super.observe()
        mViewModel.retResponse.observe(this) {
            proDialogDismiss()
        }
        mViewModel.retError.observe(this) {
            Terror(it.toString())
        }
    }

    override fun onBackPressed() {
        AppManager.instance.doubleBackToExit()
    }

    fun showGuideView() {
        GuideBuilder()
            .setTargetView(binding.baseCy)
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
            .show(this@MainActivity, null, true)
    }

    fun showGuideView2() {
        GuideBuilder()
            .setTargetView(binding.text)
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
            .show(this@MainActivity)
    }

    @SuppressLint("ResourceType")
    fun showGuideView3() {
        GuideBuilder()
            .setTargetView(binding.textView)
            .setAlpha(150)
            .setHighTargetCorner(20)
            .setHighTargetPadding(10)
            .setExitAnimationId(android.R.anim.fade_out)
            .addComponent(LottieComponent())
            .createGuide()
            .setShouldCheckLocInWindow(false)
            .show(this@MainActivity)
    }
}