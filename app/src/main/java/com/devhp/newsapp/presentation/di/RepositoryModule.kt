package com.devhp.newsapp.presentation.di

import com.devhp.newsapp.data.repository.NewsRepositoryImpl
import com.devhp.newsapp.data.repository.datasource.NewsRemoteDataSource
import com.devhp.newsapp.data.repository.datasourceImpl.NewsRemoteDataSourceImpl
import com.devhp.newsapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNesRepository(newsRemoteDataSource: NewsRemoteDataSource) : NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource)
    }
}