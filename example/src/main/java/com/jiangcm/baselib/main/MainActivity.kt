package com.jiangcm.baselib.main

import android.Manifest
import android.graphics.Color
import android.widget.Toast
import com.jiangcm.common.core.AppManager
import com.jiangcm.baselib.databinding.ActivityMainBinding
import com.jiangcm.base.base.BaseVmActivity
import com.jiangcm.base.ext.Terror
import com.jiangcm.baselib.R
import com.jiangcm.permission.PermissionX

class MainActivity : BaseVmActivity<TestViewModel>() {


    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java

    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)

    override fun initView() {
        binding.vm = mViewModel
    }

    override fun initData() {
        showProgress(cancel = false)
        mViewModel.refreshProjectList()
    }

    fun getPermission(){
        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECORD_AUDIO,
            )
            .setDialogTintColor(Color.parseColor("#1972e8"), Color.parseColor("#8ab6f5"))
            .onExplainRequestReason { scope, deniedList, beforeRequest ->
                val message = "PermissionX needs following permissions to continue"
                scope.showRequestReasonDialog(deniedList, message, "Allow", "Deny")
//                val message = "Please allow the following permissions in settings"
//                val dialog = CustomDialogFragment(message, deniedList)
//                scope.showRequestReasonDialog(dialog)
            }
            .onForwardToSettings { scope, deniedList ->
//                val message = "Please allow following permissions in settings"
//                val dialog = CustomDialogFragment(message, deniedList)
//                scope.showForwardToSettingsDialog(dialog)
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "The following permissions are denied：$deniedList", Toast.LENGTH_SHORT).show()
                }
            }
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
}