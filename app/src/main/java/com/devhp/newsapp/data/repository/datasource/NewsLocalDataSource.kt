package com.devhp.newsapp.data.repository.datasource

import com.devhp.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow


interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article)
    fun getSavedArticles(): Flow<List<Article>>

}