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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myshop.R
import com.example.myshop.model.Product


class CartAdapter(
    var clickHandler: ClickHandler
) :
    ListAdapter<Product, CartAdapter.ViewHolder>(ProductDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
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
        private val txvTitle = view.findViewById<TextView>(R.id.txv_name_list_cart)
        private val txvPrice = view.findViewById<TextView>(R.id.txv_price_list_cart)
        private val txvCount = view.findViewById<TextView>(R.id.txv_count_product)
        private val imvImage = view.findViewById<ImageView>(R.id.imv_list_cart)
        private val imvPlus = view.findViewById<ImageView>(R.id.imv_plus)
        private val imvMinus = view.findViewById<ImageView>(R.id.imv_minus)

        var productCount = 1


        @SuppressLint("SetTextI18n")
        fun bind(
            product: Product,
            clickHandler: ClickHandler
        ) {


            try {

                txvTitle.text = product.name
                Log.d("TAG", "bind: ${product.name}")
                txvPrice.text = "%,d".format(product.price.toInt()) + " تومان"
                txvCount.text = productCount.toString()
                counter(product, clickHandler)

                Glide
                    .with(view)
                    .load(product.images[0]?.src)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .transform(CenterInside(), RoundedCorners(25))
                    .placeholder(R.drawable.ic_baseline_more_horiz_24)
                    .error(R.drawable.ic_baseline_image_not_supported_24)
                    .into(imvImage)
            } catch (e: Exception) {
                Log.d("CartAdapter---TAG", "bind: $e ")

                txvTitle.text = "نام محصول"
                txvPrice.text = "%,d".format(0) + " تومان"

                imvImage.setImageResource(R.drawable.ic_baseline_image_not_supported_24)

            }

            view.setOnClickListener { clickHandler(product) }

        }

        private fun counter(product: Product, clickHandler: ClickHandler) {
            imvPlus.setOnClickListener {
                when (productCount) {
                    1 -> {
                        imvMinus.setImageResource(R.drawable.ic_baseline_remove_24)
                        productCount += 1
                    }
                    else -> productCount += 1
                }
                txvCount.text = productCount.toString()
            }
            imvMinus.setOnClickListener {
                when (productCount) {
                    2 -> {
                        imvMinus.setImageResource(R.drawable.ic_baseline_delete_outline_24)
                        productCount -= 1
                    }
                    1 -> clickHandler(product)
                    else -> productCount -= 1
                }
                txvCount.text = productCount.toString()
            }
        }
    }

}