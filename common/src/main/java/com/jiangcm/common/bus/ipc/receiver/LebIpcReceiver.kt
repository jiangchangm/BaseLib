package com.jiangcm.common.bus.ipc.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jiangcm.common.bus.LiveEventBus
import com.jiangcm.common.bus.ipc.consts.IpcConst
import com.jiangcm.common.bus.ipc.core.ProcessorManager
import java.lang.Exception

class LebIpcReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (IpcConst.ACTION == intent.action) {
            try {
                val key = intent.getStringExtra(IpcConst.KEY)
                val value: Any = ProcessorManager.getManager().createFrom(intent)
                if (key != null) {
                    LiveEventBus[key].post(value)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}