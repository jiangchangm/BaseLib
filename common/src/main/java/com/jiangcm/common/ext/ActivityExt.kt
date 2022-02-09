package com.jiangcm.common.ext

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.view.WindowInsetsController


/**
 * 设置浅色状态栏
 */
@TargetApi(Build.VERSION_CODES.R)
fun Activity.setLightStatusBar() {
    setStatusBarAppearance(true)
}

/**
 * 设置深色状态栏
 */
@TargetApi(Build.VERSION_CODES.R)
fun Activity.setDarkStatusBar() {
    setStatusBarAppearance(false)
}

/**
 * 设置状态栏外观
 * @param light 是否是浅色模式 true-浅色模式，false-深色模式
 */
@TargetApi(Build.VERSION_CODES.R)
fun Activity.setStatusBarAppearance(light: Boolean) {
    window.decorView.windowInsetsController?.setSystemBarsAppearance(
        if (light) {
            0
        } else {
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        },
        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
    )
}

/**
 * 设置decor设置状态栏是否不盖住内容
 * @param decorFitsSystemWindows true-不盖住内容，false-盖住内容
 */
@TargetApi(Build.VERSION_CODES.R)
fun Activity.setDecorFitsSystemWindows(decorFitsSystemWindows: Boolean) {
    window.setDecorFitsSystemWindows(decorFitsSystemWindows)
}

/**
 * 设置状态栏颜色
 */
fun Activity.setStatusBarColor(color: Int) {
    window.statusBarColor = color
}

/**
 * 设置导航栏颜色
 */
fun Activity.setNavigationBarColor(color: Int) {
    window.navigationBarColor = color
}

/**
 * 获取Activity的亮度
 * @return 0 ~ 1
 */
fun Activity.getBrightness() = window.attributes.screenBrightness

/**
 * 设置Activity的亮度
 * @param [brightness] 0 ~ 1
 */
fun Activity.setBrightness(brightness: Float) {
    val attributes = window.attributes
    attributes.screenBrightness = brightness
    window.attributes = attributes
}