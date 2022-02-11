package com.jiangcm.base.dialog

import android.app.Activity
import android.content.Context


class ProgressViewImpl(private val context: Context) : ProgressView {


    private var mLoadingDialog: LoadingDialog? = null


    override fun showProgressDialog(message: String, cancel: Boolean, img: Int?, logo: Int?) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog?.setLoadingText(message)
        mLoadingDialog?.setLoadImage(img)
        mLoadingDialog?.setLogoImage(logo)
        mLoadingDialog?.setCancelable(cancel)
        if (context is Activity && !context.isFinishing && mLoadingDialog?.isShowing==false) {
            mLoadingDialog?.show()
        }
    }

    override fun proDialogDismiss() {
        if (mLoadingDialog != null) mLoadingDialog?.dismiss()
        mLoadingDialog = null
    }



}