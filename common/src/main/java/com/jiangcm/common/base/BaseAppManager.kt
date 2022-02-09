package com.jiangcm.common.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.annotation.CallSuper
import com.jiangcm.common.ext.Tnormal

abstract class BaseAppManager : Application() {

    private var life: LifeListener? = null
    private var act: Activity? = null
    private var actList: MutableList<Activity> = mutableListOf()
    private var mFinalCount: Int = 0

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        life = LifeListener()
        registerActivityLifecycleCallbacks(life)
    }

    @CallSuper
    override fun onTerminate() {
        super.onTerminate()
        unregisterActivityLifecycleCallbacks(life)
    }

    private fun exit() {
        actList.forEach {
            it.finish()
        }
    }

    private fun finish(actName: String) {
        actList.forEach {
            if (it::class.java.name == actName) {
                it.finish()
            }
        }
    }

    fun finish(actClass: Class<Activity>) {
        val name = actClass::class.java.name
        finish(name)
    }


    private var exitTime: Long = 0//记录时间

    private fun doubleBackToExit(int: Int) {
        if (System.currentTimeMillis() - exitTime > int) {
            Tnormal("再按一次退出程序")
            exitTime = System.currentTimeMillis()
        } else {
            exit()
        }
    }

    fun doubleBackToExit() {
        doubleBackToExit(2000)
    }


    inner class LifeListener : ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityStarted(activity: Activity) {
            act = activity
            mFinalCount++;
            //如果mFinalCount ==1，说明是从后台到前台
            if (mFinalCount == 1) {
                //说明从后台回到了前台
                appFromBackground()
            }
        }

        override fun onActivityDestroyed(activity: Activity) {
            actList.remove(activity)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {
            mFinalCount--;
            //如果mFinalCount ==0，说明是前台到后台
            if (mFinalCount == 0) {
                //说明从前台回到了后台
                appToBackground()
            }
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            actList.add(activity)
        }
    }

    /**
     * 说明从后台回到了前台
     * 包括首次启动APP也会回调该方法
     */
    open fun appFromBackground() {

    }

    /**
     *说明从前台回到了后台
     */
    open fun appToBackground() {

    }

}