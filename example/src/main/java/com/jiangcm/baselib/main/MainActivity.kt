package com.jiangcm.baselib.main

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import com.jiangcm.base.base.activity.BaseVmDbActivity
import com.jiangcm.common.core.AppManager
import com.jiangcm.baselib.databinding.ActivityMainBinding
import com.jiangcm.base.ext.Tnormal
import com.jiangcm.base.widght.guideview.Component
import com.jiangcm.base.widght.guideview.GuideBuilder
import com.jiangcm.baselib.R
import com.jiangcm.baselib.guideView.component.LottieComponent
import com.jiangcm.baselib.guideView.component.MutiComponent
import com.jiangcm.baselib.guideView.component.SimpleComponent
import com.jiangcm.permission.PermissionX
import com.jiangcm.permission.dialog.CustomDialogFragment

class MainActivity : BaseVmDbActivity<TestViewModel,ActivityMainBinding>() {


    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java

    override fun layoutId(): Int =R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mDatabind.vm = mViewModel
        mDatabind.baseCy.post {
            showGuideView()
        }
    }

    override fun initData() {
        checkPermission()
        mViewModel.refreshProjectList()
    }

    override fun onBackPressed() {
        AppManager.instance.doubleBackToExit()
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
            .show(this@MainActivity, null, true)
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
            .show(this@MainActivity)
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
            .show(this@MainActivity)
    }

    private fun checkPermission(){
        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECORD_AUDIO,
            )
            .setDialogTintColor(Color.parseColor("#1972e8"), Color.parseColor("#8ab6f5"))
            .onExplainRequestReasonWithBeforeParam { scope, deniedList, beforeRequest ->
                val message = "Please allow the following permissions in settings"
                val dialog = CustomDialogFragment(message, deniedList)
                scope.showRequestReasonDialog(dialog)
            }
            .onForwardToSettings { scope, deniedList ->
                val message = "Please allow following permissions in settings"
                val dialog = CustomDialogFragment(message, deniedList)
                scope.showForwardToSettingsDialog(dialog)
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "权限都已获取", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "以下权限被拒绝：$deniedList", Toast.LENGTH_SHORT).show()
                }
            }
    }


}