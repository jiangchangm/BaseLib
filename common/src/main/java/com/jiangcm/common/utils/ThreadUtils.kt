package com.jiangcm.common.utils

import android.os.Looper

object ThreadUtils {
    val isMainThread: Boolean = Looper.myLooper() == Looper.getMainLooper()
}
