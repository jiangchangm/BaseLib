package com.jiangcm.base.coroutines.ui

import androidx.appcompat.app.AppCompatActivity
import com.jiangcm.base.coroutines.ui.JobHolder
import kotlinx.coroutines.Job


/**
 * 此activity会主动取消掉当前UI click中使用的协程
 */
open class JobLifecycleActivity(override val job: Job = Job()) : AppCompatActivity(), JobHolder {

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}