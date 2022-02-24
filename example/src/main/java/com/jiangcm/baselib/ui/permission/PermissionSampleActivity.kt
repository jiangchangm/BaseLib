package com.jiangcm.baselib.ui.permission

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.jiangcm.base.ext.Terror
import com.jiangcm.baselib.R
import com.jiangcm.baselib.c.ARouterPath
import com.jiangcm.baselib.databinding.ActivityPermissionSampleBinding
import com.jiangcm.baselib.ui.main.TestViewModel
import com.jiangcm.common.base.BaseActivity
import com.jiangcm.permission.PermissionX
import com.jiangcm.permission.dialog.CustomDialogFragment

@Route(path = ARouterPath.PermissionSampleAc, name = "权限申请示例页面")
class PermissionSampleActivity :
    BaseActivity<TestViewModel, ActivityPermissionSampleBinding>() {

    override fun layoutId(): Int = R.layout.activity_permission_sample

    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mDatabind.vm =mViewModel
        checkPermission()
    }

    override fun initData() {
        mViewModel.refreshProjectList()
    }

    private fun checkPermission(){
        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECORD_AUDIO,
            )
            .setDialogTintColor(Color.parseColor("#1972e8"), Color.parseColor("#8ab6f5"))
            .onExplainRequestReasonWithBeforeParam { scope, deniedList, _ ->
                val message = "Please allow the following permissions in settings"
                val dialog = CustomDialogFragment(message, deniedList)
                scope.showRequestReasonDialog(dialog)
            }
            .onForwardToSettings { scope, deniedList ->
                val message = "Please allow following permissions in settings"
                val dialog = CustomDialogFragment(message, deniedList)
                scope.showForwardToSettingsDialog(dialog)
            }
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "权限都已获取", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "以下权限被拒绝：$deniedList", Toast.LENGTH_SHORT).show()
                }
            }
    }
}