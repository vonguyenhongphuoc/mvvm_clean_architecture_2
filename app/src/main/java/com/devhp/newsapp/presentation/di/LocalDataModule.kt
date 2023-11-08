package com.devhp.newsapp.presentation.di

import com.devhp.newsapp.data.db.ArticleDAO
import com.devhp.newsapp.data.repository.datasource.NewsLocalDataSource
import com.devhp.newsapp.data.repository.datasourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(articleDAO: ArticleDAO): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDAO)
    }
}