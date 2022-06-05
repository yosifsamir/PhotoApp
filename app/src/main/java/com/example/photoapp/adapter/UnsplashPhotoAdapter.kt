package com.example.photoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.photoapp.R
import com.example.photoapp.data.UnsplashPhoto
import com.example.photoapp.databinding.UnsplashLayoutBinding

class UnsplashPhotoAdapter :
    PagingDataAdapter<UnsplashPhoto, UnsplashPhotoAdapter.UnsplashPhotoViewHolder>(PHOTO_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashPhotoViewHolder {
        val binding=UnsplashLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UnsplashPhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UnsplashPhotoViewHolder, position: Int) {
        val currentItem=getItem(position)
        if (currentItem!=null){
            holder.bind(currentItem)
        }
    }

    class UnsplashPhotoViewHolder(private val binding:UnsplashLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(unsplashPhoto: UnsplashPhoto){
            binding.apply {
                Glide.with(itemView)
                    .load(unsplashPhoto.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(unsplahImageView)
            }
        }
    }

    companion object{
        private val PHOTO_COMPARATOR= object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) = oldItem.id==newItem.id

            override fun areContentsTheSame(
                oldItem: UnsplashPhoto,
                newItem: UnsplashPhoto
            )=oldItem==newItem

        }

    }

}