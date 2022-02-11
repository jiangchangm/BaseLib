package com.jiangcm.base.utils

import android.os.Looper

object ThreadUtils {
    val isMainThread: Boolean = Looper.myLooper() == Looper.getMainLooper()
}
