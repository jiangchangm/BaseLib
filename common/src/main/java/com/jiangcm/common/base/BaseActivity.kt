package com.jiangcm.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jiangcm.common.R
import com.jiangcm.common.dialog.ProgressView
import com.jiangcm.common.dialog.ProgressViewImpl

abstract class BaseActivity : AppCompatActivity(), ProgressView {

    open val progressViewImpl: ProgressViewImpl by lazy {
        ProgressViewImpl(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
    }

    open fun layoutRes() = 0


    fun showProgress(
        message: String = getString(R.string.app_loading),
        cancel: Boolean= true,
        img: Int? = R.drawable.ic_baseline_refresh_24,
        logo: Int? = null
    ) {
        showProgressDialog(message, cancel, img, logo)
    }

    override fun showProgressDialog(message: String, cancel: Boolean, img: Int?, logo: Int?) {
        progressViewImpl.showProgressDialog(message, cancel, img, logo)
    }

    /**
     * 隐藏加载(转圈)对话框
     */
    override fun proDialogDismiss() {
        progressViewImpl.proDialogDismiss()
    }

}
