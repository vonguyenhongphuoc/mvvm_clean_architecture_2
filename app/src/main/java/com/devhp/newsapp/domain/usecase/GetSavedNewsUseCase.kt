package com.devhp.newsapp.domain.usecase

import com.devhp.newsapp.data.model.Article
import com.devhp.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow


class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {
    fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNews()
    }
}