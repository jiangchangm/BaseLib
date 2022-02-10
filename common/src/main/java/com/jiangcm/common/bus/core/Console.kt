package com.jiangcm.common.bus.core

object Console {
    /**
     * 获取控制台信息
     *
     * @return 调试信息
     */
    val info: String
        get() = LiveEventBusCore.get().console.consoleInfo
}