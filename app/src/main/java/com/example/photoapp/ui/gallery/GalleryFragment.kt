package com.example.photoapp.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData

import com.example.photoapp.R
import com.example.photoapp.adapter.UnsplashPhotoAdapter
import com.example.photoapp.data.UnsplashPhoto
import com.example.photoapp.databinding.FragmentGalleryBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery),
    UnsplashPhotoAdapter.OnItemClickListener {

    private val viewModel by viewModels<GalleryViewModel>() // the GalleryViewModel will be injected by dagger.
    private var _binding:FragmentGalleryBinding ? =null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentGalleryBinding.bind(view)
        val adapter=UnsplashPhotoAdapter(this)
        binding.apply {
            galleryRecyclerView.setHasFixedSize(true)
            galleryRecyclerView.adapter=adapter
        }

        viewModel.photos.observe(viewLifecycleOwner, object : Observer<PagingData<UnsplashPhoto>> {
            override fun onChanged(pagingUnsplashPhoto: PagingData<UnsplashPhoto>?) {
                adapter.submitData(viewLifecycleOwner.lifecycle, pagingUnsplashPhoto!!)
            }
        })
        setHasOptionsMenu(true) // show option menu in fragment
        adapter.addLoadStateListener {
            binding.galleryProgressBar.isVisible=it.source.refresh is LoadState.Loading
            binding.galleryRecyclerView.isVisible=it.source.refresh is LoadState.NotLoading
            binding.retryBtn.isVisible=it.source.refresh is LoadState.Error
            binding.errorTxt.isVisible=it.source.refresh is LoadState.Error

            if(it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached){
                binding.galleryRecyclerView.isVisible=false
                Snackbar.make(binding.root,"There is no data",Snackbar.LENGTH_LONG).show()
            }
        }

        binding.retryBtn.setOnClickListener{
            adapter.retry()  // don't use adapter.refresh()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.gallery_menu,menu)
        val searchItem=menu.findItem(R.id.action_search) // it is MenuItem not SearchView
        val searchView=searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!=null){
                    binding.galleryRecyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus() // to hide keyboard when you submit the search
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onItemClick(photo: UnsplashPhoto) {
        val action=GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }
}