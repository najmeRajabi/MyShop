package com.example.myshop.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.R
import com.example.myshop.model.Attribute
import com.example.myshop.ui.search.SearchViewModel

typealias ClickCategoryFilter = (attribute: Attribute ) -> Unit

class FilterCategoryAdapter (
    var clickHandler: ClickCategoryFilter
):
    ListAdapter<Attribute, FilterCategoryAdapter.ViewHolder>(AttributeDiffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterCategoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_categort_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterCategoryAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position)  ,clickHandler)


    }

    object AttributeDiffCallback : DiffUtil.ItemCallback<Attribute>() {
        override fun areItemsTheSame(oldItem: Attribute, newItem: Attribute): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Attribute, newItem: Attribute): Boolean {
            return oldItem.id == newItem.id
        }
    }


    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val txvListFilter = view.findViewById<TextView>(R.id.txv_category_filter_item)


        @SuppressLint("ResourceAsColor")
        fun bind(
            attribute: Attribute,
            clickHandler: ClickCategoryFilter,

        ) {

            view.setOnClickListener {
                view.setBackgroundResource(R.color._white)
                clickHandler(attribute)
            }

            txvListFilter.text = attribute.name




        }
    }

}

