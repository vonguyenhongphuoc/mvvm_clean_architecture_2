package com.devhp.newsapp.data.repository.datasource

import com.devhp.newsapp.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse>
}