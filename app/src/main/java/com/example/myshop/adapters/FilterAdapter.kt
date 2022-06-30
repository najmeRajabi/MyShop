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
import com.example.myshop.model.Terms

typealias ClickHandlerFilter = (term: Terms , checked: Boolean) -> Unit

class FilterAdapter (
    var clickHandler: ClickHandlerFilter
    ):
    ListAdapter<Terms, FilterAdapter .ViewHolder>(OptionDiffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position)  ,clickHandler)


    }

    object OptionDiffCallback : DiffUtil.ItemCallback<Terms>() {
        override fun areItemsTheSame(oldItem: Terms, newItem: Terms): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Terms, newItem: Terms): Boolean {
            return oldItem == newItem
        }
    }


    class ViewHolder( val view: View ): RecyclerView.ViewHolder(view) {
        val txvFilterItem = view.findViewById<TextView>(R.id.txv_filter_item)
        val checkBoxFilter = view.findViewById<CheckBox>(R.id.checkbox_filter_item)


        fun bind(
            terms: Terms,
            clickHandler: ClickHandlerFilter
        ) {
                txvFilterItem.text = terms.name
                checkBoxFilter.setOnCheckedChangeListener{attri , checked ->
                    clickHandler(terms, checked)

            }

        }
    }

}

