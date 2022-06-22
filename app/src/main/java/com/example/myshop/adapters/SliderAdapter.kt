package com.example.myshop.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myshop.R
import com.example.myshop.databinding.ImageSliderItemBinding
import com.example.myshop.model.Image


class SliderAdapter(val context: Context, val images: List<Image?>) :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(val binding: ImageSliderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(imageUrl: String) {

            try {
                Glide
                    .with(context)
                    .load(imageUrl)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .transform(CenterInside(), RoundedCorners(25))
                    .placeholder(R.drawable.ic_baseline_more_horiz_24)
                    .error(R.drawable.ic_baseline_image_not_supported_24)
                    .into(binding.imvDetail)
            } catch (e: Exception) {
                Log.d("SliderAdaptor---TAG", "imageView: $e ")

                binding.imvDetail.setImageResource(R.drawable.ic_baseline_image_not_supported_24)

            }
        }

    }

    override fun getItemCount(): Int = images.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {

        val binding = ImageSliderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {

        images[position]?.let { holder.setData(it.src) }
    }

}