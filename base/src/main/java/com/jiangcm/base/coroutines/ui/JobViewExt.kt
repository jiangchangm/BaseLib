package com.jiangcm.base.coroutines.ui

import UI
import android.view.View
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch

/**
 * android view的协程扩展
 */

/**
 * 给view扩展contextJob作为上下文
 * view的context如果是activity，并且实现了JobHolder的话返回job属性，在activity onDestroy的时候自动cancel掉左右协程
 */
val View.contextJob: Job
    get() = (context as? JobHolder)?.job ?: NonCancellable

/**
 * 点击事件会在主线程中处理，哪怕在子线程中调用onclick
 */
fun View.onClick(action: suspend () -> Unit) {
    setOnClickListener {
        GlobalScope.launch(contextJob + UI) {
            action()
        }
    }
}

/**
 * 点击事件会在主线程中处理
 * 并且会阻止重复点击，避免重复执行，例如登录按钮点击事件处理
 */
fun View.onClickStart(action: suspend () -> Unit) {
    val eventActor = GlobalScope.actor<Unit>(contextJob + UI) {
        for (event in channel) action()
    }
    setOnClickListener {
        eventActor.trySend(Unit).isSuccess
    }
}
