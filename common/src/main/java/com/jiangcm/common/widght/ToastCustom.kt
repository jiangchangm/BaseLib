package com.jiangcm.common.widght

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.jiangcm.common.R


object ToastCustom {

    //显示文本的Toast
    @SuppressLint("InflateParams")
    fun showTextToast(context: Context, message: String, img: Int) {
        val toastView = LayoutInflater.from(context).inflate(R.layout.toast_custom, null)
        val text = toastView.findViewById<TextView>(R.id.tv_message)
        text.text = message
        toastView.findViewById<ImageView>(R.id.img_toast).setImageResource(img)
        val toast: Toast = Toast(context)
        toast.run {
            setGravity(Gravity.CENTER, 0, 0)
            duration = Toast.LENGTH_LONG
            view = toastView
            show()
        }
    }

}