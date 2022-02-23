package com.jiangcm.baselib.ui.proxy

import android.util.Log
import android.view.View
import com.jiangcm.baselib.c.routerNav
import com.jiangcm.baselib.entity.HomeEntity

class HomeClick {

    fun adapterClick(view: View, homeEntity: HomeEntity){
        routerNav(homeEntity.path)

    }

    fun click(){
        Log.v("thss","hehe")
    }

}