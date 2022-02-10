package com.jiangcm.common.bus.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface Observable<T> {
    /**
     * 进程内发送消息
     *
     * @param value 发送的消息
     */
    fun post(value: T)

    /**
     * App内发送消息，跨进程使用
     *
     * @param value 发送的消息
     */
    fun postAcrossProcess(value: T)

    /**
     * App之间发送消息
     *
     * @param value 发送的消息
     */
    fun postAcrossApp(value: T)

    /**
     * 进程内发送消息，延迟发送
     *
     * @param value 发送的消息
     * @param delay 延迟毫秒数
     */
    fun postDelay(value: T, delay: Long)

    /**
     * 进程内发送消息，延迟发送，带生命周期
     * 如果延时发送消息的时候sender处于非激活状态，消息取消发送
     *
     * @param sender 消息发送者
     * @param value  发送的消息
     * @param delay  延迟毫秒数
     */
    fun postDelay(sender: LifecycleOwner?, value: T, delay: Long)

    /**
     * 进程内发送消息
     * 强制接收到消息的顺序和发送顺序一致
     *
     * @param value 发送的消息
     */
    fun postOrderly(value: T)

    /**
     * 以广播的形式发送一个消息
     * 需要跨进程、跨APP发送消息的时候调用该方法
     * 可使用postAcrossProcess or postAcrossApp代替
     *
     * @param value 发送的消息
     */
    @Deprecated("")
    fun broadcast(value: T)

    /**
     * 以广播的形式发送一个消息
     * 需要跨进程、跨APP发送消息的时候调用该方法
     *
     * @param value      发送的消息
     * @param foreground true:前台广播、false:后台广播
     * @param onlyInApp  true:只在APP内有效、false:全局有效
     */
    fun broadcast(value: T, foreground: Boolean, onlyInApp: Boolean)

    /**
     * 注册一个Observer，生命周期感知，自动取消订阅
     *
     * @param owner    LifecycleOwner
     * @param observer 观察者
     */
    fun observe(owner: LifecycleOwner, observer: Observer<T>)

    /**
     * 注册一个Observer，生命周期感知，自动取消订阅
     * 如果之前有消息发送，可以在注册时收到消息（消息同步）
     *
     * @param owner    LifecycleOwner
     * @param observer 观察者
     */
    fun observeSticky(owner: LifecycleOwner, observer: Observer<T>)

    /**
     * 注册一个Observer，需手动解除绑定
     *
     * @param observer 观察者
     */
    fun observeForever(observer: Observer<T>)

    /**
     * 注册一个Observer，需手动解除绑定
     * 如果之前有消息发送，可以在注册时收到消息（消息同步）
     *
     * @param observer 观察者
     */
    fun observeStickyForever(observer: Observer<T>)

    /**
     * 通过observeForever或observeStickyForever注册的，需要调用该方法取消订阅
     *
     * @param observer 观察者
     */
    fun removeObserver(observer: Observer<T>)
}