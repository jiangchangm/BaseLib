package com.jiangcm.base.widght.guideview

import android.view.LayoutInflater
import android.view.View

/**
 * * 遮罩系统中相对于目标区域而绘制一些图片或者文字等view需要实现的接口. <br></br>
 * * <br></br>
 * * [.getView] <br></br>
 * * [.getAnchor] <br></br>
 * * [.getFitPosition] <br></br>
 * * [.getXOffset] <br></br>
 * * [.getYOffset]
 * * <br></br>
 * * 具体创建遮罩的说明请参加[GuideBuilder]
 * *
 */
open interface Component {


    /**
     * 需要显示的view
     *
     * @param inflater use to inflate xml resource file
     * @return the component view
     */
    fun getView(inflater: LayoutInflater?): View?

    /**
     * 相对目标View的锚点
     *
     * @return could be [.ANCHOR_LEFT], [.ANCHOR_RIGHT],
     * [.ANCHOR_TOP], [.ANCHOR_BOTTOM], [.ANCHOR_OVER]
     */
    fun getAnchor(): Int

    /**
     * 相对目标View的对齐
     *
     * @return could be [.FIT_START], [.FIT_END],
     * [.FIT_CENTER]
     */
    fun getFitPosition(): Int

    /**
     * 相对目标View的X轴位移，在计算锚点和对齐之后。
     *
     * @return X轴偏移量, 单位 dp
     */
    fun getXOffset(): Int

    /**
     * 相对目标View的Y轴位移，在计算锚点和对齐之后。
     *
     * @return Y轴偏移量，单位 dp
     */
    fun getYOffset(): Int

    companion object {
        const val FIT_START = MaskView.LayoutParams.PARENT_START
        const val FIT_END = MaskView.LayoutParams.PARENT_END
        const val FIT_CENTER = MaskView.LayoutParams.PARENT_CENTER
        const val ANCHOR_LEFT = MaskView.LayoutParams.ANCHOR_LEFT
        const val ANCHOR_RIGHT = MaskView.LayoutParams.ANCHOR_RIGHT
        const val ANCHOR_BOTTOM = MaskView.LayoutParams.ANCHOR_BOTTOM
        const val ANCHOR_TOP = MaskView.LayoutParams.ANCHOR_TOP
        const val ANCHOR_OVER = MaskView.LayoutParams.ANCHOR_OVER

        /**
         * 圆角矩形&矩形
         */
        const val ROUNDRECT = 0

        /**
         * 圆形
         */
        const val CIRCLE = 1
        /**
         * 不定点
         */
        const val UNROUNDRECT = -1
    }
}
