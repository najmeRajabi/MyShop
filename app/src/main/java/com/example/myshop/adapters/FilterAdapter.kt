package com.example.myshop.adapters

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

typealias ClickHandlerFilter = (attribute: Attribute , isChecked: Boolean) -> Unit

class FilterAdapter (
    var clickHandler: ClickHandlerFilter
    ):
    ListAdapter<Attribute, FilterAdapter .ViewHolder>(AttributeDiffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position)  ,clickHandler,position)


    }

    object AttributeDiffCallback : DiffUtil.ItemCallback<Attribute>() {
        override fun areItemsTheSame(oldItem: Attribute, newItem: Attribute): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Attribute, newItem: Attribute): Boolean {
            return oldItem.id == newItem.id
        }
    }


    class ViewHolder( val view: View ): RecyclerView.ViewHolder(view) {
        val txvFilterItem = view.findViewById<TextView>(R.id.txv_filter_item)
        val checkBoxFilter = view.findViewById<CheckBox>(R.id.checkbox_filter_item)


        fun bind(
            attribute: Attribute,
            clickHandler: ClickHandlerFilter,
            position: Int
        ) {
                txvFilterItem.text = attribute.options[position]
                checkBoxFilter.setOnCheckedChangeListener{attri , checked ->
                    clickHandler(attribute , checked)

            }

        }
    }

}

