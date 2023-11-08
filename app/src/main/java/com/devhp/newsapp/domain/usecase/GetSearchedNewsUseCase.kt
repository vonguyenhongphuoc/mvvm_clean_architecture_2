package com.devhp.newsapp.domain.usecase

import com.devhp.newsapp.data.model.APIResponse
import com.devhp.newsapp.data.util.Resource
import com.devhp.newsapp.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country:String, searchQuery: String, page:Int): Resource<APIResponse> {
        return newsRepository.getSearchedNews(country, searchQuery, page)
    }
}