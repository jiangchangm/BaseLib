package com.jiangcm.base.ext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.NinePatchDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.jiangcm.base.R

/**
 * 染色 图标
 */
fun getTintIcon(drawable: Drawable?, @ColorInt tintColor: Int): Drawable? {
    drawable?.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
    return drawable
}

fun Context?.tint9PatchDrawableFrame(@ColorInt tintColor: Int): Drawable? {
    val toastDrawable = this.getDrawableBySdk(R.drawable.toast_frame) as NinePatchDrawable?
    return getTintIcon(toastDrawable!!, tintColor)
}

fun setBackground(view: View?, drawable: Drawable?) {
    view?.background = drawable
}

/**
 * 根据 sdk 判断选择的getDrawable方法
 */
@SuppressLint("UseCompatLoadingForDrawables")
fun Context?.getDrawableBySdk(@DrawableRes id: Int): Drawable? {
    return this?.getDrawable(id)
}