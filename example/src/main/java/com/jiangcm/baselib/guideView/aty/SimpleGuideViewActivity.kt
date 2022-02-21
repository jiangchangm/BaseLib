package com.jiangcm.baselib.guideView.aty

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.jiangcm.base.base.BaseActivity
import com.jiangcm.base.ext.Tnormal
import com.jiangcm.base.widght.guideview.Component
import com.jiangcm.base.widght.guideview.GuideBuilder
import com.jiangcm.baselib.R
import com.jiangcm.baselib.guideView.component.LottieComponent
import com.jiangcm.baselib.guideView.component.MutiComponent
import com.jiangcm.baselib.guideView.component.SimpleComponent

class SimpleGuideViewActivity : BaseActivity() {
    private var header_imgbtn: Button? = null
    private var ll_nearby: LinearLayout? = null
    private var ll_video: LinearLayout? = null
    private var ll_haha: LinearLayout? = null

    override fun getLayoutResId(): Int =R.layout.activity_simple_guide_view

    override fun initView() {
        super.initView()
        isTranslucentStatus = true
        header_imgbtn = findViewById<View>(R.id.header_imgbtn) as Button
        header_imgbtn?.setOnClickListener {
            Toast.makeText(
                this@SimpleGuideViewActivity,
                "show",
                Toast.LENGTH_SHORT
            ).show()
        }
        ll_haha = findViewById<View>(R.id.ll_haha) as LinearLayout
        ll_nearby = findViewById<View>(R.id.ll_nearby) as LinearLayout
        ll_video = findViewById<View>(R.id.ll_video) as LinearLayout
        header_imgbtn?.post { showGuideView2() }
    }


    fun showGuideView() {
        val builder = GuideBuilder()
        builder.setTargetView(ll_haha)
            .setAlpha(255)
        builder.setOnVisibilityChangedListener(object : GuideBuilder.OnVisibilityChangedListener {
            override fun onShown() {}
            override fun onDismiss() {
                showGuideView3()
            }
        })
        val component = SimpleComponent()
        builder.addComponent(component)
        val guide = builder.createGuide()
        component.setOnListener {
            Tnormal("hahaha")
            guide.dismiss()
        }
        guide.show(this@SimpleGuideViewActivity, null, true)
    }

    fun showGuideView2() {
        val builder1 = GuideBuilder()
        builder1.setTargetView(ll_nearby)
            .setAlpha(150)
            .setHighTargetGraphStyle(Component.CIRCLE)
        builder1.setOnVisibilityChangedListener(object : GuideBuilder.OnVisibilityChangedListener {
            override fun onShown() {}
            override fun onDismiss() {
                showGuideView()
            }
        })
        builder1.addComponent(MutiComponent())
        val guide = builder1.createGuide()
        guide.show(this@SimpleGuideViewActivity)
    }

    @SuppressLint("ResourceType")
    fun showGuideView3() {
        val builder1 = GuideBuilder()
        builder1.setTargetView(ll_video)
            .setAlpha(150)
            .setHighTargetCorner(20)
            .setHighTargetPadding(10)
            .setExitAnimationId(android.R.anim.fade_out)
        builder1.setOnVisibilityChangedListener(object : GuideBuilder.OnVisibilityChangedListener {
            override fun onShown() {}
            override fun onDismiss() {}
        })
        builder1.addComponent(LottieComponent())
        val guide = builder1.createGuide()
        guide.setShouldCheckLocInWindow(false)
        guide.show(this@SimpleGuideViewActivity)
    }
}