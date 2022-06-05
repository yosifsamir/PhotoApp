package com.example.photoapp.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.photoapp.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository):
    ViewModel() {

    companion object{
        private const val DEFAULT_QUERY="cats"
    }
    private val currentQuery=MutableLiveData(DEFAULT_QUERY)
    val photos=currentQuery.switchMap { queryString->
        unsplashRepository.getSearchResult(queryString).cachedIn(viewModelScope) }
    // switchMap will be triggered when currentQuery changed.
    // cachedIn(viewModelScope) to cache this livedata otherwise you get error when you rotate the device because you cannot load the same paging data twice.

    fun searchPhotos(query:String){
        currentQuery.value=query
    }

}