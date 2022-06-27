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
import com.example.myshop.ui.home.HomeFragment

enum class Orientation {
    VERTICAL,
    HORIZONTAL
}

typealias ClickHandler = (product: Product) -> Unit

class HomeListsAdapter(
    var orientation: Orientation,
    var clickHandler: ClickHandler
                        ) :
    ListAdapter<Product, HomeListsAdapter.ViewHolder>(ProductDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_list_item, parent, false)
        val viewRow = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item_row, parent, false)

        return if (orientation == Orientation.HORIZONTAL){
            ViewHolder(view)
        }else{
            ViewHolder(viewRow)
        }

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
        private val txvTitle = view.findViewById<TextView>(R.id.txv_name_list_Home)
        private val txvPrice = view.findViewById<TextView>(R.id.txv_price_list_Home)
        private val txvRate = view.findViewById<TextView>(R.id.txv_rateCount_item)
        private val imvImage = view.findViewById<ImageView>(R.id.imv_list_home)
        private val imvRate = view.findViewById<ImageView>(R.id.imv_rate_item)


        @SuppressLint("SetTextI18n")
        fun bind(
            product: Product,
            clickHandler: ClickHandler
        ) {
            if (product.id != 608) {


                try {

                    txvTitle.text = product.name
                    txvPrice.text = "%,d".format(product.price.toInt()) + " تومان"
                    txvRate.text = product.averageRating.toString()
                    product.ratingCount?.let { rateImage(it, imvRate) }

                    Glide
                        .with(view)
                        .load(product.images[0]?.src)
                        .centerCrop()
                        .transition(withCrossFade())
                        .transform(CenterInside(), RoundedCorners(25))
                        .placeholder(R.drawable.ic_baseline_more_horiz_24)
                        .error(R.drawable.ic_baseline_image_not_supported_24)
                        .into(imvImage)
                } catch (e: Exception) {
                    Log.d("HomeAdaptor---TAG", "bind: $e ")

                    txvTitle.text = "name"
                    txvPrice.text = "%,d".format(0) + " تومان"
                    txvRate.text = " "

                    imvImage.setImageResource(R.drawable.ic_baseline_image_not_supported_24)

                }

                view.setOnClickListener { clickHandler(product) }

            }

        }

        private fun rateImage(ratingCount: Int , imageView: ImageView) {
            if (ratingCount == 0){
                imageView.setImageResource(R.drawable.ic_baseline_star_outline_24)
            }else{
                imageView.setImageResource(R.drawable.ic_baseline_star_rate_24)

            }
        }
    }

}