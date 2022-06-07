package com.example.photoapp.ui.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.photoapp.R
import com.example.photoapp.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentDetailsBinding.bind(view)
        val photo=args.data
        Glide.with(this@DetailsFragment)
            .load(photo.urls.full)
            .error(R.drawable.ic_error)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.detailsProgressBar.isVisible=false

                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.detailsProgressBar.isVisible=false
                    binding.descriptionDetailsTxt.isVisible=photo.description !=null

                    return false
                }
            })
            .override(500,600)
            .into(binding.imageDetails)
        binding.descriptionDetailsTxt.text=photo.description

//        binding.descriptionDetailsTxt.paint.isUnderlineText=true

    }

}