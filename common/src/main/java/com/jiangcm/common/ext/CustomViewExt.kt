package com.jiangcm.common.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


//绑定普通的Recyclerview
fun RecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): RecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll

    closeDefaultAnimator(this)
    return this
}

private fun closeDefaultAnimator(mRvCustomer: RecyclerView?) {
    if (null == mRvCustomer) return
    mRvCustomer.itemAnimator?.run {
        addDuration = 0
        changeDuration = 0
        moveDuration = 0
        removeDuration = 0
        (mRvCustomer.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

    }
}






//设置适配器的列表动画
fun BaseQuickAdapter<*, *>.setAdapterAnimion(mode: Int) {
    //等于0，关闭列表动画 否则开启
    if (mode == 0) {
        this.animationEnable = false
    } else {
        this.animationEnable = true
        this.setAnimationWithDefault(BaseQuickAdapter.AnimationType.values()[mode - 1])
    }
}

fun ViewPager.init(
    fm: FragmentManager,
    fragments: ArrayList<Fragment>,
    titles: Array<String> = arrayOf()
): FragmentPagerAdapter {

    val fPAdapter = object : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            // 覆写destroyItem并且空实现,这样每个Fragment中的视图就不会被销毁
            // super.destroyItem(container, position, object);
        }

        override fun getPageTitle(position: Int): CharSequence? = titles[position]

        override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE

    }
    adapter = fPAdapter
    //设置适配器
    return fPAdapter
}

fun ViewPager2.init(
    fragment: FragmentManager,
    fragments: ArrayList<Fragment>,
    lifecycle: Lifecycle,
    isUserInputEnabled: Boolean = true
): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment, lifecycle) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}
