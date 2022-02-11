package com.jiangcm.base.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.jiangcm.base.R

class LoadingDialog(context: Context) : Dialog(context, R.style.LoadingStyle) {

    private var mTvLoadingText: TextView? = null
    private var loadingView: ImageView? = null
    private var logoView: ImageView? = null

    init {
        initView()
    }

    @SuppressLint("InflateParams")
    private fun initView() {
        val view: View = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
        mTvLoadingText = view.findViewById(R.id.tvMessage)
        logoView = view.findViewById(R.id.imageView)
        loadingView = view.findViewById(R.id.loadingView)

        setContentView(view)
        view.postDelayed({ startAnimation(loadingView) }, 350)
    }


    private fun startAnimation(view: View?) {
        view?.let {
            val animation: Animation = RotateAnimation(
                0f,
                359f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            animation.fillAfter = false
            animation.duration = 1500
            animation.repeatCount = -1
            animation.interpolator = LinearInterpolator()
            it.startAnimation(animation)
        }
    }

    fun setLoadImage(loadingImg: Int?) {
        loadingImg?.let {
            loadingView?.setImageResource(it)
        }
    }

    fun setLogoImage(logo: Int?) {
        logoView?.visibility = View.GONE
        logo?.let {
            logoView?.visibility = View.VISIBLE
            logoView?.setImageResource(it)
        }
    }

    fun setLoadingText(loadingText: String) {
        mTvLoadingText?.text = loadingText
    }
}
