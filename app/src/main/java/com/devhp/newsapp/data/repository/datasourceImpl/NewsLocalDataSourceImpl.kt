package com.devhp.newsapp.data.repository.datasourceImpl

import com.devhp.newsapp.data.db.ArticleDAO
import com.devhp.newsapp.data.model.Article
import com.devhp.newsapp.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val articleDAO: ArticleDAO,
) : NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        articleDAO.insert(article)
    }

    override  fun getSavedArticles(): Flow<List<Article>> {
        return articleDAO.getAllArticles()
    }
}