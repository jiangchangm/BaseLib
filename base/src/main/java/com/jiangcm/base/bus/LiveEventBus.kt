package com.jiangcm.base.bus

import com.jiangcm.base.bus.core.Config
import com.jiangcm.base.bus.core.LiveEvent
import com.jiangcm.base.bus.core.LiveEventBusCore
import com.jiangcm.base.bus.core.Observable

object LiveEventBus {
    /**
     * get observable by key with type
     *
     * @param key
     * @param type
     * @param <T>
     * @return Observable<T>
    </T></T> */
    operator fun <T> get(key: String, type: Class<T>): Observable<T> {
        return LiveEventBusCore.get().with(key,type)
    }

    /**
     * get observable by key
     *
     * @param key
     * @return Observable<Object>
    </Object> */
    operator fun get(key: String): Observable<Any> {
        return get(key, Any::class.java)
    }

    /**
     * get observable from eventType
     *
     * @param eventType
     * @param <T>
     * @return Observable<T>
    </T></T> */
    operator fun <T : LiveEvent?> get(eventType: Class<T>): Observable<T> {
        return LiveEventBus[eventType.name, eventType]
    }

    /**
     * use the inner class Config to set params
     * first of all, call config to get the Config instance
     * then, call the method of Config to config LiveEventBus
     * call this method in Application.onCreate
     */
    fun config(): Config {
        return LiveEventBusCore.get().config()
    }
}