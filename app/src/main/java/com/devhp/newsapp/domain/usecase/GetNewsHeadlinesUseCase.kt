package com.devhp.newsapp.domain.usecase

import com.devhp.newsapp.data.model.APIResponse
import com.devhp.newsapp.data.util.Resource
import com.devhp.newsapp.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(): Resource<APIResponse> {
        return newsRepository.getNewsHeadlines()
    }
}