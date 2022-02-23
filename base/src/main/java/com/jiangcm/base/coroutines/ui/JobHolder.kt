package com.jiangcm.base.coroutines.ui

import kotlinx.coroutines.Job


interface JobHolder {
    val job: Job
}