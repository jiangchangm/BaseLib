package com.jiangcm.common.bus.core

import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.jiangcm.common.bus.ipc.consts.IpcConst
import com.jiangcm.common.bus.ipc.core.ProcessorManager
import com.jiangcm.common.bus.ipc.receiver.LebIpcReceiver
import com.jiangcm.common.log.LogUtils.Companion.log
import com.jiangcm.common.utils.AppUtils.getApp
import com.jiangcm.common.utils.BasePropertyUtils
import com.jiangcm.common.utils.ThreadUtils.isMainThread
import java.lang.ClassCastException
import java.lang.Exception
import java.lang.StringBuilder
import java.util.HashMap
import java.util.logging.Level

class LiveEventBusCore private constructor() {
    /**
     * 单例模式实现
     */
    private object SingletonHolder {
        val DEFAULT_BUS = LiveEventBusCore()
    }

    /**
     * 存放LiveEvent
     */
    private val bus: MutableMap<String, LiveEvent<Any>>

    /**
     * 可配置的项
     */
    private val config = Config()
    private var lifecycleObserverAlwaysActive: Boolean
    private var autoClear: Boolean

    /**
     * 跨进程通信
     */
    private val receiver: LebIpcReceiver
    private var isRegisterReceiver = false

    /**
     * 调试
     */
    val console: InnerConsole = InnerConsole()
    @Synchronized
    fun <T> with(key: String, type: Class<T>): Observable<T> {
        if (!bus.containsKey(key)) {
            bus[key] = LiveEvent(key)
        }
        return bus[key] as Observable<T>
    }

    /**
     * use the class Config to set params
     * first of all, call config to get the Config instance
     * then, call the method of Config to config LiveEventBus
     * call this method in Application.onCreate
     */
    fun config(): Config {
        return config
    }

    fun registerReceiver() {
        if (isRegisterReceiver) {
            return
        }
        val application = getApp()
        val intentFilter = IntentFilter()
        intentFilter.addAction(IpcConst.ACTION)
        application.registerReceiver(receiver, intentFilter)
        isRegisterReceiver = true
    }

    fun setLifecycleObserverAlwaysActive(lifecycleObserverAlwaysActive: Boolean) {
        this.lifecycleObserverAlwaysActive = lifecycleObserverAlwaysActive
    }

    fun setAutoClear(autoClear: Boolean) {
        this.autoClear = autoClear
    }

    private inner class LiveEvent<T>(private val key: String) :
        Observable<T> {
        val liveData: LifecycleLiveData<T>
        private val observerMap: MutableMap<Observer<*>, ObserverWrapper<T>> = HashMap()
        private val mainHandler = Handler(Looper.getMainLooper())

        /**
         * 进程内发送消息
         *
         * @param value 发送的消息
         */
        override fun post(value: T) {
            if (isMainThread) {
                postInternal(value)
            } else {
                mainHandler.post(PostValueTask(value))
            }
        }

        /**
         * App内发送消息，跨进程使用
         *
         * @param value 发送的消息
         */
        override fun postAcrossProcess(value: T) {
            broadcast(value, foreground = false, onlyInApp = true)
        }

        /**
         * App之间发送消息
         *
         * @param value 发送的消息
         */
        override fun postAcrossApp(value: T) {
            broadcast(value, foreground = false, onlyInApp = false)
        }

        /**
         * 进程内发送消息，延迟发送
         *
         * @param value 发送的消息
         * @param delay 延迟毫秒数
         */
        override fun postDelay(value: T, delay: Long) {
            mainHandler.postDelayed(PostValueTask(value), delay)
        }

        /**
         * 进程内发送消息，延迟发送，带生命周期
         * 如果延时发送消息的时候sender处于非激活状态，消息取消发送
         *
         * @param sender 消息发送者
         * @param value 发送的消息
         * @param delay 延迟毫秒数
         */
        override fun postDelay(sender: LifecycleOwner?, value: T, delay: Long) {
            mainHandler.postDelayed(PostLifeValueTask(value, sender), delay)
        }

        /**
         * 进程内发送消息
         * 强制接收到消息的顺序和发送顺序一致
         *
         * @param value 发送的消息
         */
        override fun postOrderly(value: T) {
            mainHandler.post(PostValueTask(value))
        }

        /**
         * App之间发送消息
         *
         * @param value 发送的消息
         */
        @Deprecated("", ReplaceWith("broadcast(value, foreground = false, onlyInApp = false)"))
        override fun broadcast(value: T) {
            broadcast(value, foreground = false, onlyInApp = false)
        }

        /**
         * 以广播的形式发送一个消息
         * 需要跨进程、跨APP发送消息的时候调用该方法
         *
         * @param value      发送的消息
         * @param foreground true:前台广播、false:后台广播
         * @param onlyInApp  true:只在APP内有效、false:全局有效
         */
        override fun broadcast(value: T, foreground: Boolean, onlyInApp: Boolean) {
            if (isMainThread) {
                broadcastInternal(value, foreground, onlyInApp)
            } else {
                mainHandler.post { broadcastInternal(value, foreground, onlyInApp) }
            }
        }

        /**
         * 注册一个Observer，生命周期感知，自动取消订阅
         *
         * @param owner    LifecycleOwner
         * @param observer 观察者
         */
        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            if (isMainThread) {
                observeInternal(owner, observer)
            } else {
                mainHandler.post { observeInternal(owner, observer) }
            }
        }

        /**
         * 注册一个Observer，生命周期感知，自动取消订阅
         * 如果之前有消息发送，可以在注册时收到消息（消息同步）
         *
         * @param owner    LifecycleOwner
         * @param observer 观察者
         */
        override fun observeSticky(owner: LifecycleOwner, observer: Observer<T>) {
            if (isMainThread) {
                observeStickyInternal(owner, observer)
            } else {
                mainHandler.post { observeStickyInternal(owner, observer) }
            }
        }

        /**
         * 注册一个Observer，需手动解除绑定
         *
         * @param observer 观察者
         */
        override fun observeForever(observer: Observer<T>) {
            if (isMainThread) {
                observeForeverInternal(observer)
            } else {
                mainHandler.post { observeForeverInternal(observer) }
            }
        }

        /**
         * 注册一个Observer，需手动解除绑定
         * 如果之前有消息发送，可以在注册时收到消息（消息同步）
         *
         * @param observer 观察者
         */
        override fun observeStickyForever(observer: Observer<T>) {
            if (isMainThread) {
                observeStickyForeverInternal(observer)
            } else {
                mainHandler.post { observeStickyForeverInternal(observer) }
            }
        }

        /**
         * 通过observeForever或observeStickyForever注册的，需要调用该方法取消订阅
         *
         * @param observer 观察者
         */
        override fun removeObserver(observer: Observer<T>) {
            if (isMainThread) {
                removeObserverInternal(observer)
            } else {
                mainHandler.post { removeObserverInternal(observer) }
            }
        }

        @MainThread
        private fun postInternal(value: T) {
            log(Level.INFO, "post: $value with key: $key")
            liveData.value = value
        }

        @MainThread
        private fun broadcastInternal(value: T, foreground: Boolean, onlyInApp: Boolean) {
            log(
                Level.INFO, "broadcast: " + value + " foreground: " + foreground +
                        " with key: " + key
            )
            val application = getApp()
            val intent = Intent(IpcConst.ACTION)
            if (foreground) {
                intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
            }
            if (onlyInApp) {
                intent.setPackage(application.packageName)
            }
            intent.putExtra(IpcConst.KEY, key)
            val handle = ProcessorManager.getManager().writeTo(intent, value)
            try {
                if (handle) {
                    application.sendBroadcast(intent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @MainThread
        private fun observeInternal(owner: LifecycleOwner, observer: Observer<T>) {
            val observerWrapper: ObserverWrapper<T> = ObserverWrapper(observer)
            observerWrapper.preventNextEvent = liveData.version > ExternalLiveData.START_VERSION
            liveData.observe(owner, observerWrapper)
            log(
                Level.INFO, "observe observer: " + observerWrapper + "(" + observer + ")"
                        + " on owner: " + owner + " with key: " + key
            )
        }

        @MainThread
        private fun observeStickyInternal(owner: LifecycleOwner, observer: Observer<T>) {
            val observerWrapper: ObserverWrapper<T> = ObserverWrapper(observer)
            liveData.observe(owner, observerWrapper)
            log(
                Level.INFO, "observe sticky observer: " + observerWrapper + "(" + observer + ")"
                        + " on owner: " + owner + " with key: " + key
            )
        }

        @MainThread
        private fun observeForeverInternal(observer: Observer<T>) {
            val observerWrapper: ObserverWrapper<T> = ObserverWrapper(observer)
            observerWrapper.preventNextEvent = liveData.version > ExternalLiveData.START_VERSION
            observerMap[observer] = observerWrapper
            liveData.observeForever(observerWrapper)
            log(
                Level.INFO, "observe forever observer: " + observerWrapper + "(" + observer + ")"
                        + " with key: " + key
            )
        }

        @MainThread
        private fun observeStickyForeverInternal(observer: Observer<T>) {
            val observerWrapper: ObserverWrapper<T> = ObserverWrapper(observer)
            observerMap[observer] = observerWrapper
            liveData.observeForever(observerWrapper)
            log(
                Level.INFO,
                "observe sticky forever observer: " + observerWrapper + "(" + observer + ")"
                        + " with key: " + key
            )
        }

        @MainThread
        private fun removeObserverInternal(observer: Observer<T>) {
            val realObserver: Observer<T>? = if (observerMap.containsKey(observer)) {
                observerMap.remove(observer)
            } else {
                observer
            }
            realObserver?.let {
                liveData.removeObserver(realObserver)
            }
        }

        inner class LifecycleLiveData<T> : ExternalLiveData<T>() {
            override fun observerActiveLevel(): Lifecycle.State {
                return if (lifecycleObserverAlwaysActive) Lifecycle.State.CREATED else Lifecycle.State.STARTED
            }

            override fun removeObserver(observer: Observer<in T>) {
                super.removeObserver(observer)
                if (autoClear && !liveData.hasObservers()) {
                    get().bus.remove(key)
                }
                log(Level.INFO, "observer removed: $observer")
            }
        }

        private inner class PostValueTask(private val newValue: T) : Runnable {
            override fun run() {
                postInternal(newValue)
            }
        }

        private inner class PostLifeValueTask(
            private val newValue: T,
            private val owner: LifecycleOwner?
        ) :
            Runnable {
            override fun run() {
                if (owner != null) {
                    if (owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                        postInternal(newValue)
                    }
                }
            }
        }

        init {
            liveData = LifecycleLiveData()
        }
    }

    private inner class ObserverWrapper<T>(private val observer: Observer<T>) :
        Observer<T> {
        var preventNextEvent = false
        override fun onChanged(t: T?) {
            if (preventNextEvent) {
                preventNextEvent = false
                return
            }
            log(Level.INFO, "message received: $t")
            try {
                observer.onChanged(t)
            } catch (e: ClassCastException) {
                log(
                    Level.WARNING,
                    "class cast error on message received: $t", e
                )
            } catch (e: Exception) {
                log(
                    Level.WARNING,
                    "error on message received: $t", e
                )
            }
        }

    }

    inner class InnerConsole {
        val consoleInfo: String
            get() {
                val sb = StringBuilder()
                sb.append("*********Base info*********").append("\n")
                sb.append(baseInfo)
                sb.append("*********Event info*********").append("\n")
                sb.append(busInfo)
                return sb.toString()
            }
        private val baseInfo: String
            get() {
                val sb = StringBuilder()
                sb.append("lifecycleObserverAlwaysActive: ").append(lifecycleObserverAlwaysActive)
                    .append("\n")
                    .append("autoClear: ").append(autoClear).append("\n")
                    .append("logger enable: ")
                    .append(BasePropertyUtils.getPropertiesByName(BasePropertyUtils.IS_DEBUG))
                    .append("\n")
                    .append("Receiver register: ").append(isRegisterReceiver).append("\n")
                    .append("Application: ").append(getApp()).append("\n")
                return sb.toString()
            }
        private val busInfo: String
            get() {
                val sb = StringBuilder()
                for (key in bus.keys) {
                    sb.append("Event name: $key").append("\n")
                    val liveData: LiveEvent<Any>.LifecycleLiveData<Any>? = bus[key]?.liveData
                    liveData?.let {
                        sb.append("\tversion: " + liveData.version).append("\n")
                        sb.append("\thasActiveObservers: " + liveData.hasActiveObservers()).append("\n")
                        sb.append("\thasObservers: " + liveData.hasObservers()).append("\n")
                        sb.append("\tActiveCount: " + getActiveCount(liveData)).append("\n")
                        sb.append("\tObserverCount: " + getObserverCount(liveData)).append("\n")
                        sb.append("\tObservers: ").append("\n")
                        sb.append("\t\t" + getObserverInfo(liveData)).append("\n")
                    }
                }
                return sb.toString()
            }

        private fun getActiveCount(liveData: LiveData<*>): Int {
            return try {
                val field = LiveData::class.java.getDeclaredField("mActiveCount")
                field.isAccessible = true
                field[liveData] as Int
            } catch (e: Exception) {
                -1
            }
        }

        private fun getObserverCount(liveData: LiveData<*>): Int {
            return try {
                val field = LiveData::class.java.getDeclaredField("mObservers")
                field.isAccessible = true
                val mObservers = field[liveData]
                val classOfSafeIterableMap: Class<*> = mObservers.javaClass
                val size = classOfSafeIterableMap.getDeclaredMethod("size")
                size.isAccessible = true
                size.invoke(mObservers) as Int
            } catch (e: Exception) {
                -1
            }
        }

        private fun getObserverInfo(liveData: LiveData<*>): String {
            return try {
                val field = LiveData::class.java.getDeclaredField("mObservers")
                field.isAccessible = true
                field[liveData]?.toString()?:""
            } catch (e: Exception) {
                ""
            }
        }
    }

    companion object {
        fun get(): LiveEventBusCore {
            return SingletonHolder.DEFAULT_BUS
        }
    }

    init {
        bus = HashMap()
        lifecycleObserverAlwaysActive = true
        autoClear = false
        receiver = LebIpcReceiver()
        registerReceiver()
    }
}
