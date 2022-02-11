package com.jiangcm.base.log

import android.util.Log
import java.util.logging.Level

class LogUtils {

    companion object {
        private const val TAG = "[LiveEventBus]"

        fun log(level: Level?, msg: String?) {
            when (level) {
                Level.SEVERE -> {
                    Log.e(TAG, msg ?: "")
                }
                Level.WARNING -> {
                    Log.w(TAG, msg ?: "")
                }
                Level.INFO -> {
                    Log.i(TAG, msg ?: "")
                }
                Level.CONFIG -> {
                    Log.d(TAG, msg ?: "")
                }
                else -> {
                    Log.v(TAG, msg ?: "")
                }
            }
        }

        fun logV(tag: String?, str: String?) {
            if (com.jiangcm.base.utils.BasePropertyUtils.getPropertiesByName(com.jiangcm.base.utils.BasePropertyUtils.IS_DEBUG) == "true") {
                Log.v(tag, str?:"")
            }
        }
        fun logE(tag: String?, str: String?) {
            if (com.jiangcm.base.utils.BasePropertyUtils.getPropertiesByName(com.jiangcm.base.utils.BasePropertyUtils.IS_DEBUG) == "true") {
                Log.e(tag, str?:"")
            }
        }
        fun logD(tag: String?, str: String?) {
            if (com.jiangcm.base.utils.BasePropertyUtils.getPropertiesByName(com.jiangcm.base.utils.BasePropertyUtils.IS_DEBUG) == "true") {
                Log.d(tag, str?:"")
            }
        }
        fun logI(tag: String?, str: String?) {
            if (com.jiangcm.base.utils.BasePropertyUtils.getPropertiesByName(com.jiangcm.base.utils.BasePropertyUtils.IS_DEBUG) == "true") {
                Log.i(tag, str?:"")
            }
        }


        fun log(level: Level?, msg: String?, th: Throwable?) {
            when (level) {
                Level.SEVERE -> {
                    Log.e(TAG, msg, th)
                }
                Level.WARNING -> {
                    Log.w(TAG, msg, th)
                }
                Level.INFO -> {
                    Log.i(TAG, msg, th)
                }
                Level.CONFIG -> {
                    Log.d(TAG, msg, th)
                }
                Level.OFF -> {
                    Log.v(TAG, msg, th)
                }
            }
        }
    }
}