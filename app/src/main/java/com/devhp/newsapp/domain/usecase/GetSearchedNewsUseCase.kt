package com.devhp.newsapp.domain.usecase

import com.devhp.newsapp.data.model.APIResponse
import com.devhp.newsapp.data.util.Resource
import com.devhp.newsapp.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(searchQuery: String): Resource<APIResponse> {
        return newsRepository.getSearchedNews(searchQuery)
    }
}