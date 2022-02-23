package com.jiangcm.baselib.c

import com.jiangcm.baselib.entity.HomeEntity

object Const {
    const val PARAMS_DATA = "params_data"

    val homeItemData = arrayListOf(
        HomeEntity("遮罩层基础功能", ARouterPath.GuideSampleAc),
        HomeEntity("权限申请", ARouterPath.PermissionSampleAc),
        HomeEntity("外链网页", ARouterPath.WebAc)
    )
}