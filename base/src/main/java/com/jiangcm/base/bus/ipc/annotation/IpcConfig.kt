package com.jiangcm.base.bus.ipc.annotation

import kotlin.reflect.KClass


@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Target
annotation class IpcConfig(val processor: KClass<out Any>)
