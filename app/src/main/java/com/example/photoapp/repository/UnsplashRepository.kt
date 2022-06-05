package com.example.photoapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.photoapp.api.UnsplashApi
import com.example.photoapp.datasource.UnsplashPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    fun getSearchResult(query:String)= Pager(config = PagingConfig(pageSize = 20,maxSize = 100,enablePlaceholders = false)
        ,pagingSourceFactory = {UnsplashPagingSource(unsplashApi,query)}).liveData
}