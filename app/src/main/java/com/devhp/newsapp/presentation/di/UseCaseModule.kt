package com.devhp.newsapp.presentation.di

import com.devhp.newsapp.domain.repository.NewsRepository
import com.devhp.newsapp.domain.usecase.GetNewsHeadlinesUseCase
import com.devhp.newsapp.domain.usecase.GetSavedNewsUseCase
import com.devhp.newsapp.domain.usecase.GetSearchedNewsUseCase
import com.devhp.newsapp.domain.usecase.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetNewsHeadlinesUseCase(newsRepository: NewsRepository): GetNewsHeadlinesUseCase {
        return GetNewsHeadlinesUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideGetSearchedNewsUseCase(newsRepository: NewsRepository): GetSearchedNewsUseCase {
        return GetSearchedNewsUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideSaveNewsUseCase(newsRepository: NewsRepository): SaveNewsUseCase {
        return SaveNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedNewsUseCase(newsRepository: NewsRepository): GetSavedNewsUseCase {
        return GetSavedNewsUseCase(newsRepository)
    }
}