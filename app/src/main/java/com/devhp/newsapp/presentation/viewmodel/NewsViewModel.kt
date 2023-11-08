package com.devhp.newsapp.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.devhp.newsapp.data.model.APIResponse
import com.devhp.newsapp.data.model.Article
import com.devhp.newsapp.data.util.Resource
import com.devhp.newsapp.domain.usecase.GetNewsHeadlinesUseCase
import com.devhp.newsapp.domain.usecase.GetSavedNewsUseCase
import com.devhp.newsapp.domain.usecase.GetSearchedNewsUseCase
import com.devhp.newsapp.domain.usecase.SaveNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSaveNewsUseCase: GetSavedNewsUseCase,
) :
    AndroidViewModel(app) {
    val newsHeadLines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadLines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadLines.postValue(Resource.Loading())
        try {
            if (isInternetAvailable(app)) {
                val apiResult = getNewsHeadlinesUseCase.execute(country, page)
                newsHeadLines.postValue(apiResult)
            } else {
                newsHeadLines.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            newsHeadLines.postValue(Resource.Error(e.message.toString()))
        }


    }


    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
        return result
    }

    val searchedNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun searchNews(country: String, searchQuery: String, page: Int) = viewModelScope.launch {
        searchedNews.postValue(Resource.Loading())
        try {
            if (isInternetAvailable(app)) {
                val response = getSearchedNewsUseCase.execute(country, searchQuery, page)
                searchedNews.postValue(response)
            } else {
                searchedNews.postValue(Resource.Error("No internet connection"))
            }
        } catch (e: Exception) {
            searchedNews.postValue(Resource.Error(e.message.toString()))
        }

    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        saveNewsUseCase.execute(article)
    }

    fun getSavedNews() = liveData {
        getSaveNewsUseCase.execute().collect {
            emit(it)
        }
    }

}