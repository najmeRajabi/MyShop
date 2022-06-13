package com.example.myshop.adapters

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
import com.example.myshop.model.Category

typealias ClickHandlerCategory = (category: Category) -> Unit

class CategoryAdaptor(var clickHandler: ClickHandlerCategory):
    ListAdapter<Category, CategoryAdaptor.ViewHolder>(CtegoryDiffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdaptor.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_list_item, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdaptor.ViewHolder, position: Int) {
        holder.bind(getItem(position) , clickHandler )


    }

    object CtegoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }
    }


    class ViewHolder( val view: View): RecyclerView.ViewHolder(view) {
        val txvTitle = view.findViewById<TextView>(R.id.txv_name_list_category)
        val imvList = view.findViewById<ImageView>(R.id.imv_list_category)


        fun bind(
            category: Category,
            clickHandler: ClickHandlerCategory
        ) {

            txvTitle.text = category.name
            view.setOnClickListener { clickHandler(category) }

            Glide
                .with(view.context)
                .load(category.image?.src)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(CenterInside() , RoundedCorners(25) )
                .placeholder(R.drawable.ic_baseline_more_horiz_24)
                .error(R.drawable.ic_baseline_image_not_supported_24)
                .into(imvList)



        }
    }

}