package com.jiangcm.baselib.ui.main

import com.jiangcm.common.api.RetrofitClient

class MainRepository {

    suspend fun getProjectList(id: Int) = RetrofitClient.apiService.getProjectList(id)

}