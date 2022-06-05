package com.example.photoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.photoapp.api.UnsplashApi
import com.example.photoapp.data.UnsplashResponse
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var unsplashApi: UnsplashApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        unsplashApi.searchPhotosLive("cat","1","20").enqueue(object : Callback<UnsplashResponse> {
            override fun onFailure(call: Call<UnsplashResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<UnsplashResponse>,
                response: Response<UnsplashResponse>
            ) {
                Log.d("ddd",response.body()!!.results.toString())
            }

        })
    }
}