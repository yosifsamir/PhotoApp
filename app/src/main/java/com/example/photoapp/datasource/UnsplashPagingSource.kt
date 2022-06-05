package com.example.photoapp.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.photoapp.api.UnsplashApi
import com.example.photoapp.data.UnsplashPhoto
import retrofit2.HttpException
import java.io.IOException

class UnsplashPagingSource(private val unsplashApi: UnsplashApi,private val query:String ) : PagingSource<Int, UnsplashPhoto> (){
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
       return try {
           var posotion = params.key
           if (posotion == null) posotion = 1
           val response = unsplashApi.searchPhotos(
               query,
               page = posotion.toString(),
               perPage = params.loadSize.toString()
           )
           val photos = response.results
           Log.d("ddd",query)
           Log.d("ddd",posotion.toString())
           Log.d("ddd",params.loadSize.toString())
           LoadResult.Page(data=photos, prevKey = if(posotion==1) null else posotion-1,
               nextKey = if(photos.isEmpty()) null else posotion+1
           )
       }catch (exception : IOException){
           LoadResult.Error(exception)
       }catch (exception:HttpException){
           LoadResult.Error(exception)
       }
    }

}