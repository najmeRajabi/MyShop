package com.example.myshop.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.myshop.R
import com.example.myshop.model.Product


typealias ClickHandler = (product: Product) -> Unit

class HomeListsAdapter(var clickHandler: ClickHandler) :
    ListAdapter<Product, HomeListsAdapter.ViewHolder>(ProductDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_list_item, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeListsAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position), clickHandler)


    }

    object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txvTitle = view.findViewById<TextView>(R.id.txv_name_list_Home)
        val txvPrice = view.findViewById<TextView>(R.id.txv_price_list_Home)
        val imvImage = view.findViewById<ImageView>(R.id.imv_list_home)


        @SuppressLint("SetTextI18n")
        fun bind(
            product: Product,
            clickHandler: ClickHandler
        ) {

            txvTitle.text = product.name
            txvPrice.text = product.price+ " تومان"
            try {

                Glide
                    .with(view)
                    .load(product.images[0].src)
                    .centerCrop()
                    .transition(withCrossFade())
                    .transform(CenterInside(), RoundedCorners(25))
                    .placeholder(R.drawable.ic_baseline_more_horiz_24)
                    .error(R.drawable.ic_baseline_image_not_supported_24)
                    .into(imvImage)
            } catch (e: Exception) {
                Log.d("HomeAdaptor---TAG", "bind: $e ")

                imvImage.setImageResource(R.drawable.ic_baseline_image_not_supported_24)

            }

            view.setOnClickListener { clickHandler(product) }

        }
    }

}