package com.devhp.newsapp.domain.repository

import com.devhp.newsapp.data.model.APIResponse
import com.devhp.newsapp.data.model.Article
import com.devhp.newsapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadlines(country:String,page:Int): Resource<APIResponse>
    suspend fun getSearchedNews(searchQuery:String): Resource<APIResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>

}