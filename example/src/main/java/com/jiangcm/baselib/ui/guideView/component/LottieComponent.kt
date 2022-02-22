package com.jiangcm.baselib.ui.guideView.component

import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.jiangcm.base.widght.guideview.Component
import com.jiangcm.baselib.R

class LottieComponent : Component {

    var listener: (() -> Unit?)? = null

    fun setOnListener(listener: () -> Unit?) {
        this.listener = listener
    }


    override fun getView(inflater: LayoutInflater?): View {
        val ll = inflater?.inflate(R.layout.layer_lottie, null) as LinearLayout
        ll.setOnClickListener { view ->
            Toast.makeText(view.context, "引导层被点击了", Toast.LENGTH_SHORT).show()
        }
        return ll
    }

    override fun getAnchor(): Int {
        return Component.ANCHOR_TOP
    }

    override fun getFitPosition(): Int {
        return Component.FIT_CENTER
    }

    override fun getXOffset(): Int {
        return 0
    }

    override fun getYOffset(): Int {
        return -30
    }
}