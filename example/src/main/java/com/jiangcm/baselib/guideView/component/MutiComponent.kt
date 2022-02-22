package com.jiangcm.baselib.guideView.component

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.jiangcm.base.widght.guideview.Component
import com.jiangcm.baselib.R

class MutiComponent : Component {
    override fun getView(inflater: LayoutInflater?): View? {
        val ll = LinearLayout(inflater!!.context)
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        ll.orientation = LinearLayout.VERTICAL
        ll.layoutParams = param
        val textView = TextView(inflater.context)
        textView.setText(R.string.nearby)
        textView.setTextColor(inflater.context.resources.getColor(R.color.color_white))
        textView.textSize = 20f
        val imageView = ImageView(inflater.context)
        imageView.setImageResource(R.mipmap.arrow)
        ll.removeAllViews()
        ll.addView(textView)
        ll.addView(imageView)
        ll.setOnClickListener { view ->
            Toast.makeText(view.context, "引导层被点击了", Toast.LENGTH_SHORT).show()
        }
        return ll
    }

    override fun getAnchor(): Int {
        return Component.ANCHOR_BOTTOM
    }

    override fun getFitPosition(): Int {
        return Component.FIT_CENTER
    }

    override fun getXOffset(): Int {
        return 0
    }

    override fun getYOffset(): Int {
        return 50
    }
}
