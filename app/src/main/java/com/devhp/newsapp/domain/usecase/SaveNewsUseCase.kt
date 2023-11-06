package com.devhp.newsapp.domain.usecase

import com.devhp.newsapp.data.model.Article
import com.devhp.newsapp.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}