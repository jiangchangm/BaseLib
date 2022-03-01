package com.jiangcm.baselib.c

import android.app.Activity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.jiangcm.baselib.R
import com.jiangcm.common.core.AppManager
import com.jiangcm.common.widght.ToastCustom

object ARouterPath {

    /**
     * 组名
     */
    private const val APP = "/app" //宿主 App 组件
    private const val OTHER = "/other"//其他



    const val MainAc = "$APP/MainActivity"
    const val GuideSampleAc = "$APP/GuideSampleActivity"
    const val PermissionSampleAc = "$APP/PermissionSampleActivity"
    const val NavigationButtonAc = "$APP/NavigationButtonActivity"
    const val WebAc = "$APP/WebActivity"
    const val TestAc = "$APP/TestActivity"
}

/**
 * 路由普通跳转
 * @param path 跳转路径，必填
 * @see Const.ARouterPath 配置路由
 *
 * @param params 可选参数
 *
 */
fun routerNav(path: String, params: Bundle? = null) : ARouter {
    val instance = ARouter.getInstance()
    instance
        .build(path)
        .with(params)
        .navigation()

    return instance
}

/**
 * 路由带返回值跳转
 *
 * @param context 当前activity
 *
 * @param path 跳转路径，必填
 * @see Const.ARouterPath 配置路由
 *
 * @param resultCode 返回码
 * @param params 可选参数
 */
fun routerNavForResult(context: Activity, path: String, resultCode: Int, params: Bundle? = null) {
    ARouter.getInstance()
        .build(path)
        .with(params)
        .navigation(context, resultCode)
}

/**
 * @param route 跳转参数
 * @param jump 1不跳转 3跳转到web 2APP内部跳转
 */
fun routerNavUtils(route: String, jump: Int = 0) {
    if (route.isEmpty() || jump == 0) return

    try {
        when (jump) {
            1 -> showMessage(AppManager.instance.getString(R.string.toast_jump_fail))

            2 -> {
                // app 内链跳转 如：/activities/ActivitiesDetail?params_data=1
                val router = route.split("?")
                if (router.size > 1) {
                    routerNav(router[0], Bundle().apply {
                        // paramsData: params_data=1
                        val paramsData = router[1].split("=")
                        putString(Const.PARAMS_DATA, paramsData[1])
                    })
                } else {
                    routerNav(router[0])
                }
            }
            // 跳转的是外链
            3 -> {
                routerNav(ARouterPath.WebAc, Bundle().apply {
                    putString(Const.PARAMS_DATA, route)
                })
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        showMessage(AppManager.instance.getString(R.string.toast_jump_err))
    }
}

fun showMessage(message: String, img: Int = com.jiangcm.common.R.drawable.ic_toast) {
    ToastCustom.showTextToast(AppManager.instance, message, img)
}