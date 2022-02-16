package com.jiangcm.baselib.main

import com.jiangcm.common.api.RetrofitClient

class MainRepository {

    suspend fun getProjectList(id: Int) = RetrofitClient.apiService.getProjectList(id)

}