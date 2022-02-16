package com.jiangcm.dependencies

object AndroidConfig {
    const val compileSdkVersion = 30
    const val minSdkVersion = 21
    const val targetSdkVersion = 32

    //版本号和版本名称
    const val versionCode = 1
    const val versionName = "1.0.0"

    const val AndroidJUnitRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object AppId {
    const val applicationId = "com.jiangcm.baselib"
    const val applicationId_ivt = "com.jiangcm.baselib.ivt"
}

object SigningConfigs {
    const val storePassword = "zaq12wsx"
    const val keyAlias = "axie"
    const val keyPassword = "zaq12wsx"
}