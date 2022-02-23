package com.jiangcm.base.base.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.annotation.CheckResult
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jiangcm.base.R
import com.jiangcm.base.base.vm.BaseViewModel
import com.jiangcm.base.dialog.ProgressView
import com.jiangcm.base.dialog.ProgressViewImpl
import com.jiangcm.base.ext.immersiveStatusBar
import com.jiangcm.base.ext.setDecorFitsSystemWindows
import com.jiangcm.base.ext.setStatusBarColor
import com.jiangcm.base.ext.setStatusBarDarkTheme
import com.jiangcm.base.lifecycle.*
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseVmActivity<VM : BaseViewModel> : AppCompatActivity(),
    LifecycleProvider<ActivityEvent>, ProgressView {

    //是否沉浸式,默认沉浸式
    var isTranslucentStatus: Boolean = true

    //沉浸后是否留出状态栏高度，默认不留高度
    var isfitSystemWindows: Boolean = false

    protected open lateinit var mViewModel: VM

    /**
     * 是否需要使用DataBinding 供子类BaseVmDbActivity修改，用户请慎动
     */
    private var isUserDb = false


    abstract fun layoutId(): Int

    private val progressViewImpl: ProgressViewImpl by lazy {
        ProgressViewImpl(this)
    }

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
        if (!isUserDb) {
            setContentView(layoutId())
        } else {
            initDataBind()
        }
        init(savedInstanceState)
    }


    private fun init(savedInstanceState: Bundle?) {
        initViewModel()
        registerUiChange()
        observe()
        initView(savedInstanceState)
        initData()
        setTranslucentStatus()
    }
    /**
     * 注册 UI 事件
     */
    private fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observe(this, Observer {
            showProgress(
                it.ifEmpty {
                    "请求网络中..."
                }
            )
        })
        //关闭弹窗
        mViewModel.loadingChange.dismissDialog.observe(this, Observer {
            proDialogDismiss()
        })
    }
    /**
     * 获取ViewModel的class
     */
    protected abstract fun viewModelClass(): Class<VM>

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    fun userDataBinding(isUserDb: Boolean) {
        this.isUserDb = isUserDb
    }
    /**
     * 订阅，LiveData、Bus
     */
    open fun observe() {}

    open fun initView(savedInstanceState: Bundle?) {}
    open fun initData() {}
    /**
     * 供子类BaseVmDbActivity 初始化Databinding操作
     */
    open fun initDataBind() {}

    /**
     * 加载(转圈)对话框
     */
    fun showProgress(
        message: String = getString(R.string.app_loading),
        cancel: Boolean = true,
        img: Int? = R.drawable.ic_baseline_refresh_24,
        logo: Int? = null
    ) {
        showProgressDialog(message, cancel, img, logo)
    }

    /**
     * 加载(转圈)对话框
     */
    override fun showProgressDialog(message: String, cancel: Boolean, img: Int?, logo: Int?) {
        progressViewImpl.showProgressDialog(message, cancel, img, logo)
    }

    /**
     * 隐藏加载(转圈)对话框
     */
    override fun proDialogDismiss() {
        progressViewImpl.proDialogDismiss()
    }

    /**
     * 设置沉浸式
     */
    open fun setTranslucentStatus() {
        // 判断是否需要沉浸式
        immersiveStatusBar(isTranslucentStatus)
        //当FitsSystemWindows设置 true 时 且为沉浸式时候，会在屏幕最上方预留出状态栏高度的 padding
        setDecorFitsSystemWindows(isTranslucentStatus && isfitSystemWindows)
        //设置状态栏透明
        setStatusBarColor(Color.TRANSPARENT)
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!setStatusBarDarkTheme(true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            setStatusBarColor(0x55000000)
        }
    }

    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    @CheckResult
    override fun lifecycle(): Observable<ActivityEvent> = lifecycleSubject.hide()

    @CheckResult
    override fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> =
        RxLifecycle.bindUntilEvent(lifecycleSubject, event)

    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> =
        RxLifecycleAndroid.bindActivity(lifecycleSubject)

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
    }

}
