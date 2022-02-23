package com.jiangcm.baselib.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.jiangcm.baselib.R
import com.jiangcm.baselib.databinding.ItemHomeBinding
import com.jiangcm.baselib.entity.HomeEntity
import com.jiangcm.baselib.ui.proxy.HomeClick

class HomeAdapter : BaseQuickAdapter<HomeEntity, BaseDataBindingHolder<ItemHomeBinding>>(R.layout.item_home){

    override fun convert(holder: BaseDataBindingHolder<ItemHomeBinding>, item: HomeEntity) {
        val dataBinding = holder.dataBinding
        if (dataBinding != null) {
            dataBinding.vm = item
            dataBinding.click = HomeClick()
        }
    }

}