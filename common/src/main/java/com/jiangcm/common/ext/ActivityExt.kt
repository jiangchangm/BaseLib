package com.jiangcm.common.ext

import android.R
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.annotation.IntDef
import androidx.appcompat.app.AppCompatActivity
import com.jiangcm.common.utils.OSUtils


const val TYPE_MIUI = 0
const val TYPE_FLYME = 1
const val TYPE_M = 3 //6.0

@IntDef(TYPE_MIUI, TYPE_FLYME, TYPE_M)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
internal annotation class ViewType

/**
 * 沉浸式系统UI，将系统状态栏设为透明，并且主布局沉浸到系统状态栏下面。
 * @param [light] true-状态栏字体和图标为黑色，false-状态栏图标为白色
 */
fun AppCompatActivity.immersiveStatusBar(light: Boolean = true) {
    if (Build.VERSION.SDK_INT >= 30) {
        setDecorFitsSystemWindows(false)
        setStatusBarAppearance(light)
        setStatusBarColor(Color.TRANSPARENT)
    } else {
        window.run {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            decorView.systemUiVisibility =
                if (light) {
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                }
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }
    }
}

/**
 * 设置浅色状态栏
 */
@TargetApi(Build.VERSION_CODES.R)
fun AppCompatActivity.setLightStatusBar() {
    setStatusBarAppearance(true)
}

/**
 * 设置深色状态栏
 */
@TargetApi(Build.VERSION_CODES.R)
fun AppCompatActivity.setDarkStatusBar() {
    setStatusBarAppearance(false)
}

/**
 * 设置状态栏外观
 * @param light 是否是浅色模式 true-浅色模式，false-深色模式
 */
@TargetApi(Build.VERSION_CODES.R)
fun AppCompatActivity.setStatusBarAppearance(light: Boolean) {
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
fun AppCompatActivity.setDecorFitsSystemWindows(decorFitsSystemWindows: Boolean) {
    if (Build.VERSION.SDK_INT >= 30) {
        window.setDecorFitsSystemWindows(decorFitsSystemWindows)
    }else{
        val winContent:ViewGroup = findViewById<ViewGroup>(R.id.content)
        if (winContent.childCount > 0) {
            val rootView = winContent.getChildAt(0) as? ViewGroup
            rootView?.fitsSystemWindows = decorFitsSystemWindows
        }
    }

}


/**
 * 设置状态栏颜色
 */
fun AppCompatActivity.setStatusBarColor(color: Int) {
    window.statusBarColor = color
}

/**
 * 设置导航栏颜色
 */
fun AppCompatActivity.setNavigationBarColor(color: Int) {
    window.navigationBarColor = color
}

/**
 * 获取AppCompatActivity的亮度
 * @return 0 ~ 1
 */
fun AppCompatActivity.getBrightness() = window.attributes.screenBrightness

/**
 * 设置AppCompatActivity的亮度
 * @param [brightness] 0 ~ 1
 */
fun AppCompatActivity.setBrightness(brightness: Float) {
    val attributes = window.attributes
    attributes.screenBrightness = brightness
    window.attributes = attributes
}


/**
 * 设置状态栏深色浅色切换
 */
fun AppCompatActivity.setStatusBarDarkTheme(dark: Boolean): Boolean {
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            setStatusBarFontIconDark(
                this,
                TYPE_M,
                dark
            )
        }
        OSUtils.isMiui() -> {
            setStatusBarFontIconDark(
                this,
                TYPE_MIUI,
                dark
            )
        }
        OSUtils.isFlyme() -> {
            setStatusBarFontIconDark(
                this,
                TYPE_FLYME,
                dark
            )
        }
        else -> { //其他情况
            false
        }
    }
}

/**
 * 设置 状态栏深色浅色切换
 */
private fun setStatusBarFontIconDark(
    activity: AppCompatActivity?,
    @ViewType type: Int,
    dark: Boolean
): Boolean {
    return when (type) {
        TYPE_MIUI -> setMiuiUI(
            activity,
            dark
        )
        TYPE_FLYME -> setFlymeUI(
            activity,
            dark
        )
        TYPE_M -> setCommonUI(
            activity,
            dark
        )
        else -> setCommonUI(activity, dark)
    }
}

//设置6.0 状态栏深色浅色切换
private fun setCommonUI(activity: AppCompatActivity?, dark: Boolean): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decorView = activity?.window?.decorView
        if (decorView != null) {
            var vis = decorView.systemUiVisibility
            vis = if (dark) {
                vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            if (decorView.systemUiVisibility != vis) {
                decorView.systemUiVisibility = vis
            }
            return true
        }
    }
    return false
}

//设置Flyme 状态栏深色浅色切换
private fun setFlymeUI(activity: AppCompatActivity?, dark: Boolean): Boolean {
    return try {
        val window = activity?.window
        val lp = window?.attributes
        val darkFlag =
            WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
        val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
        darkFlag.isAccessible = true
        meizuFlags.isAccessible = true
        val bit = darkFlag.getInt(null)
        var value = meizuFlags.getInt(lp)
        value = if (dark) {
            value or bit
        } else {
            value and bit.inv()
        }
        meizuFlags.setInt(lp, value)
        if (window != null) {
            window.attributes = lp
        }
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

//设置MIUI 状态栏深色浅色切换
private fun setMiuiUI(activity: AppCompatActivity?, dark: Boolean): Boolean {
    return try {
        val window = activity?.window
        val clazz: Class<*>? = activity?.window?.javaClass
        @SuppressLint("PrivateApi") val layoutParams =
            Class.forName("android.view.MiuiWindowManager\$LayoutParams")
        val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
        val darkModeFlag = field.getInt(layoutParams)
        val extraFlagField = clazz?.getDeclaredMethod(
            "setExtraFlags",
            Int::class.javaPrimitiveType,
            Int::class.javaPrimitiveType
        )
        extraFlagField?.isAccessible = true
        if (dark) {    //状态栏亮色且黑色字体.
            extraFlagField?.invoke(window, darkModeFlag, darkModeFlag)
        } else {
            extraFlagField?.invoke(window, 0, darkModeFlag)
        }
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

//获取状态栏高度
fun AppCompatActivity.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

