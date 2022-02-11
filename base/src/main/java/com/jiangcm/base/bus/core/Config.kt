package com.jiangcm.base.bus.core

import android.content.Context

class Config {
    /**
     * lifecycleObserverAlwaysActive
     * set if then observer can always receive message
     * true: observer can always receive message
     * false: observer can only receive message when resumed
     *
     * @param active
     * @return
     */
    fun lifecycleObserverAlwaysActive(active: Boolean): Config {
        LiveEventBusCore.get().setLifecycleObserverAlwaysActive(active)
        return this
    }

    /**
     * @param clear
     * @return true: clear livedata when no observer observe it
     * false: not clear livedata unless app was killed
     */
    fun autoClear(clear: Boolean): Config {
        LiveEventBusCore.get().setAutoClear(clear)
        return this
    }

    /**
     * config broadcast
     * only if you called this method, you can use broadcastValue() to send broadcast message
     *
     * @param context
     * @return
     */
    fun setContext(context: Context?): Config {
        com.jiangcm.base.utils.AppUtils.init(context)
        LiveEventBusCore.get().registerReceiver()
        return this
    }
}