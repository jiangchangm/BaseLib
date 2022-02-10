package com.jiangcm.base.core

import com.alibaba.android.arouter.launcher.ARouter
import com.jiangcm.base.utils.PropertyUtils
import com.jiangcm.common.base.BaseAppManager
import kotlin.properties.Delegates

@SuppressWarnings("all")
class AppManager : BaseAppManager() {

    var token:String?=null

    companion object {
        @JvmStatic
        var instance: AppManager by Delegates.notNull()
    }


    override fun onCreate() {
        super.onCreate()
        instance =this
        initRouter()
    }

    private fun initRouter() {
        if (isApkInDebug()) {     // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)
    }

    private fun isApkInDebug():Boolean{
        return PropertyUtils.getPropertiesByName(PropertyUtils.IS_DEBUG) == "true"
    }
}