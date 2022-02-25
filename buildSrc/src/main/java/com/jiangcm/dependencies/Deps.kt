package com.jiangcm.dependencies

object DepsVersion {
    const val kotlinVersion = "1.6.10"
    const val kotlinxCoroutinesVersion = "1.6.0"
    const val rxJavaVersion = "2.2.7"
    const val rxAndroidVersion = "2.1.1"
    const val retrofitSdkVersion = "2.9.0"
    const val lifecycleVersion = "2.2.0"
    const val dataBindingVersion = "4.1.3"
    const val constraintlayoutVersion = "2.0.4"
    const val arouterVersion = "1.5.2"
    const val materialSdk = "1.3.0"
    const val appcompatSdk = "1.2.0"
    const val coreKtxSdk = "1.3.2"
    const val activityVersion = "1.2.4"
    const val roomVersion = "2.2.5"
    const val fragmentVersion = "1.3.6"
    const val jsoupVersion = "1.14.3"
    const val viewHolderVersion = "3.0.7"
    const val jsr305Version = "3.0.2"
    const val leakcanary = "2.8.1"
    const val ereza = "2.3.0"
    const val lottie = "4.2.1"
    const val materialDialogs = "3.3.0"
    const val mmkv = "1.2.12"
}


object Deps {
    //kotlin
    const val kotlinStdlib =
        "org.jetbrains.kotlin:kotlin-stdlib:${DepsVersion.kotlinVersion}"
    const val kotlinxCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DepsVersion.kotlinxCoroutinesVersion}"

    //appcompat
    const val appcompat = "androidx.appcompat:appcompat:${DepsVersion.appcompatSdk}"
    const val activity = "androidx.activity:activity-ktx:${DepsVersion.activityVersion}"
    const val fragment = "androidx.fragment:fragment-ktx:${DepsVersion.fragmentVersion}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${DepsVersion.constraintlayoutVersion}"

    //material
    const val material =
        "com.google.android.material:material:${DepsVersion.materialSdk}"

    //core-ktx
    const val corektx = "androidx.core:core-ktx:${DepsVersion.coreKtxSdk}"

    //com.alibaba:arouter-api
    const val arouterApi = "com.alibaba:arouter-api:${DepsVersion.arouterVersion}"
    const val arouterCompiler = "com.alibaba:arouter-compiler:${DepsVersion.arouterVersion}"

    //rx
    const val rxJava = "io.reactivex.rxjava2:rxjava:${DepsVersion.rxJavaVersion}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${DepsVersion.rxAndroidVersion}"

    //lifecycle_version
    const val lifecycleLivedataktx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${DepsVersion.lifecycleVersion}"
    const val lifecycleViewmodelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${DepsVersion.lifecycleVersion}"
    const val lifecycleProcess =
        "androidx.lifecycle:lifecycle-process:${DepsVersion.lifecycleVersion}"
    const val lifecycleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${DepsVersion.lifecycleVersion}"

    const val databinding =
        "androidx.databinding:databinding-runtime:${DepsVersion.dataBindingVersion}"
    const val roomRuntime = "androidx.room:room-runtime:${DepsVersion.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${DepsVersion.roomVersion}"

    //retrofit
    const val retrofit =
        "com.squareup.retrofit2:retrofit:${DepsVersion.retrofitSdkVersion}"
    const val retrofitConverterGson =
        "com.squareup.retrofit2:converter-gson:${DepsVersion.retrofitSdkVersion}"

    //解析html
    const val jsoup = "org.jsoup:jsoup:${DepsVersion.jsoupVersion}"

    //代码检测注释
    const val jsr305Annotations = "com.google.code.findbugs:jsr305:${DepsVersion.jsr305Version}"
    const val BaseRecyclerViewAdapterHelper =
        "com.github.CymChad:BaseRecyclerViewAdapterHelper:${DepsVersion.viewHolderVersion}"

    //内存泄漏检查
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${DepsVersion.leakcanary}"

    // 防崩溃
    const val ereza = "cat.ereza:customactivityoncrash:${DepsVersion.ereza}"

    //lottie动画
    const val lottie = "com.airbnb.android:lottie:${DepsVersion.lottie}"

    // 微信开源项目，替代SP
    val mmkv = "com.tencent:mmkv:${DepsVersion.mmkv}"
}

object Material {
    const val materialDialogsCore = "com.afollestad.material-dialogs:core:${DepsVersion.materialDialogs}"
    const val materialDialogsInput = "com.afollestad.material-dialogs:input:${DepsVersion.materialDialogs}"
    const val materialDialogsBottomSheets = "com.afollestad.material-dialogs:bottomsheets:${DepsVersion.materialDialogs}"
    const val materialDialogsLifecycle = "com.afollestad.material-dialogs:lifecycle:${DepsVersion.materialDialogs}"
}

