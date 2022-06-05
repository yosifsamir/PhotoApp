package com.example.photoapp.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagingData

import com.example.photoapp.R
import com.example.photoapp.adapter.UnsplashPhotoAdapter
import com.example.photoapp.data.UnsplashPhoto
import com.example.photoapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val viewModel by viewModels<GalleryViewModel>() // the GalleryViewModel will be injected by dagger.
    private var _binding:FragmentGalleryBinding ? =null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentGalleryBinding.bind(view)
        val adapter=UnsplashPhotoAdapter()
        binding.apply {
            galleryRecyclerView.setHasFixedSize(true)
            galleryRecyclerView.adapter=adapter
        }

        viewModel.photos.observe(viewLifecycleOwner, object : Observer<PagingData<UnsplashPhoto>> {
            override fun onChanged(pagingUnsplashPhoto: PagingData<UnsplashPhoto>?) {
                adapter.submitData(viewLifecycleOwner.lifecycle, pagingUnsplashPhoto!!)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}