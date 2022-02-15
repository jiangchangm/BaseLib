package com.jiangcm.base.bus.ipc.core

import android.content.Intent
import android.os.Bundle
import com.jiangcm.base.bus.ipc.annotation.IpcConfig
import com.jiangcm.base.bus.ipc.consts.IpcConst
import java.util.*

class ProcessorManager {

    companion object{
        private object SingletonHolder {
            val INSTANCE = ProcessorManager()
        }

        fun getManager(): ProcessorManager {
            return SingletonHolder.INSTANCE
        }
    }

    private var baseProcessors: List<Processor>? = null
    private var processorMap: MutableMap<String, Processor>? = null

    init{
        baseProcessors = LinkedList(
            listOf(
                StringProcessor(),
                IntProcessor(),
                BooleanProcessor(),
                DoubleProcessor(),
                FloatProcessor(),
                LongProcessor(),
                SerializableProcessor(),
                ParcelableProcessor()
            )
        )
        processorMap = HashMap()
        for (processor in baseProcessors as LinkedList<Processor>) {
            (processorMap as HashMap<String, Processor>)[processor.javaClass.name] = processor
        }
    }

    private fun ProcessorManager() {}

    fun writeTo(intent: Intent?, value: Any?): Boolean {
        if (intent == null || value == null) {
            return false
        }
        val bundle = Bundle()
        var processed = false
        //用指定的processor处理
        val config = value.javaClass.getAnnotation(IpcConfig::class.java)
        if (config != null) {
            val processorType: Class<out Processor> = config.processor as Class<out Processor>
            val processorTypeName = processorType.name
            if (!processorMap!!.containsKey(processorTypeName)) {
                try {
                    processorMap!![processorTypeName] = processorType.newInstance()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            val processor = processorMap!![processorTypeName]
            if (processor != null) {
                try {
                    val handle = processor.writeToBundle(bundle, value)
                    if (handle) {
                        intent.putExtra(IpcConst.KEY_PROCESSOR_NAME, processor.javaClass.name)
                        intent.putExtra(IpcConst.KEY_BUNDLE, bundle)
                        processed = true
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if (processed) {
                return true
            }
        }
        //用默认的processor处理
        for (processor in baseProcessors!!) {
            try {
                val handle = processor.writeToBundle(bundle, value)
                if (handle) {
                    intent.putExtra(IpcConst.KEY_PROCESSOR_NAME, processor.javaClass.name)
                    intent.putExtra(IpcConst.KEY_BUNDLE, bundle)
                    processed = true
                    break
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return processed
    }

    fun createFrom(intent: Intent?): Any? {
        if (intent == null) {
            return null
        }
        val processorName = intent.getStringExtra(IpcConst.KEY_PROCESSOR_NAME)
        val bundle = intent.getBundleExtra(IpcConst.KEY_BUNDLE)
        if (processorName == null || processorName.isEmpty() || bundle == null) {
            return null
        }
        if (processorMap?.containsKey(processorName) == false) {
            try {
                processorMap!![processorName] =
                    Class.forName(processorName).newInstance() as Processor
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val processor = processorMap!![processorName] ?: return null
        return try {
            processor.createFromBundle(bundle)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}