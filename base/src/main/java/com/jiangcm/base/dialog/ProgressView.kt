package com.jiangcm.base.dialog

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface ProgressView {

    @SuppressLint("SupportAnnotationUsage")
    fun showProgressDialog(
        @StringRes message: String,
        cancel: Boolean = false,
        @DrawableRes img: Int?,
        @DrawableRes logo: Int?
    )

    fun proDialogDismiss()
}