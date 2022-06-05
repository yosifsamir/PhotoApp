package com.example.photoapp.api

import androidx.lifecycle.LiveData
import com.example.photoapp.data.UnsplashResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    companion object{
        const val CLIENT_ID="n-fUBXe_KjZoo5U_v6A1djJCmeLP-2xCIEWUHT_xi3g"
        const val BASE_URL="https://api.unsplash.com/"
    }

    @Headers("Accept-version: v1",
                     "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(@Query("query") query:String
                             ,@Query("page") page:String
                             ,@Query("per_page") perPage:String):UnsplashResponse


    @Headers("Accept-version: v1",
                     "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    fun searchPhotosLive(@Query("query") query:String
                             ,@Query("page") page:String
                             ,@Query("per_page") perPage:String):Call<UnsplashResponse>

}