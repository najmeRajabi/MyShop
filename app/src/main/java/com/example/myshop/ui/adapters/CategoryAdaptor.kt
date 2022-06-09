package com.example.myshop.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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


        fun bind(
            category: Category,
            clickHandler: ClickHandlerCategory
        ) {

            txvTitle.text = category.name
            view.setOnClickListener { clickHandler(category) }

        }
    }

}