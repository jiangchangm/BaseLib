package com.jiangcm.common.bus.ipc.annotation;

import com.jiangcm.common.bus.ipc.core.Processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IpcConfig {

    Class<? extends Processor> processor();
}
