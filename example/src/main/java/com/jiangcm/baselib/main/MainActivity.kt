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
        showProgress(cancel = false)
        mViewModel.refreshProjectList()
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
        val builder = GuideBuilder()
        builder.setTargetView(binding.baseCy)
            .setAlpha(150)
            .setHighTargetGraphStyle(Component.UNROUNDRECT)
        builder.setOnVisibilityChangedListener(object : GuideBuilder.OnVisibilityChangedListener {
            override fun onShown() {}
            override fun onDismiss() {
                showGuideView2()
            }
        })
        val component = SimpleComponent()
        builder.addComponent(component)
        val guide = builder.createGuide()
        component.setOnListener {
            Tnormal("hahaha")
            guide.dismiss()
        }
        guide.show(this@MainActivity, null, true)
    }

    fun showGuideView2() {
        val builder1 = GuideBuilder()
        builder1.setTargetView(binding.text)
            .setAlpha(150)
            .setHighTargetGraphStyle(Component.CIRCLE)
        builder1.setOnVisibilityChangedListener(object : GuideBuilder.OnVisibilityChangedListener {
            override fun onShown() {}
            override fun onDismiss() {
                showGuideView3()
            }
        })
        builder1.addComponent(MutiComponent())
        val guide = builder1.createGuide()
        guide.show(this@MainActivity)
    }

    @SuppressLint("ResourceType")
    fun showGuideView3() {
        val builder1 = GuideBuilder()
        builder1.setTargetView(binding.textView)
            .setAlpha(150)
            .setHighTargetCorner(20)
            .setHighTargetPadding(10)
            .setExitAnimationId(android.R.anim.fade_out)
        builder1.addComponent(LottieComponent())
        val guide = builder1.createGuide()
        guide.setShouldCheckLocInWindow(false)
        guide.show(this@MainActivity)
    }
}