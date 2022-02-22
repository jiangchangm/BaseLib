package com.jiangcm.baselib.ui.guideView.component

import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.jiangcm.base.widght.guideview.Component
import com.jiangcm.baselib.R

class SimpleComponent : Component {

    var listener: (() -> Unit?)? = null

    fun setOnListener(listener: () -> Unit?): SimpleComponent {
        this.listener = listener
        return this
    }

    override fun getView(inflater: LayoutInflater?): View {
        val ll = inflater?.inflate(R.layout.layer_frends, null) as ConstraintLayout
        ll.setOnClickListener { view ->
            listener?.invoke()
        }
        return ll
    }

    override fun getAnchor(): Int = Component.ANCHOR_OVER
    override fun getFitPosition(): Int = Component.FIT_CENTER
    override fun getXOffset(): Int = 0
    override fun getYOffset(): Int = 0
}