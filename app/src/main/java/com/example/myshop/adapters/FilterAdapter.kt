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
import com.example.myshop.model.Options

typealias ClickHandlerFilter = (option: String , checked: Boolean) -> Unit

class FilterAdapter (
    var clickHandler: ClickHandlerFilter
    ):
    ListAdapter<String, FilterAdapter .ViewHolder>(OptionDiffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position)  ,clickHandler)


    }

    object OptionDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }


    class ViewHolder( val view: View ): RecyclerView.ViewHolder(view) {
        val txvFilterItem = view.findViewById<TextView>(R.id.txv_filter_item)
        val checkBoxFilter = view.findViewById<CheckBox>(R.id.checkbox_filter_item)


        fun bind(
            options: String,
            clickHandler: ClickHandlerFilter
        ) {
                txvFilterItem.text = options
                checkBoxFilter.setOnCheckedChangeListener{attri , checked ->
                    clickHandler(options, checked)

            }

        }
    }

}

