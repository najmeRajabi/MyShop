package com.example.myshop.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.R
import com.example.myshop.model.Product
import com.example.myshop.model.Review

typealias ClickHandlerReview = (review: Review) -> Unit

class ReviewAdapter(
    val clickDelete: ClickHandlerReview,
    val clickEdit: ClickHandlerReview) :
    ListAdapter<Review, ReviewAdapter.ViewHolder>(ReviewDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_item, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position) ,clickDelete,clickEdit )


    }

    object ReviewDiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val txvReviewer = view.findViewById<TextView>(R.id.txv_reviewer)
        private val txvReview = view.findViewById<TextView>(R.id.txv_review)
        private val txvRate = view.findViewById<TextView>(R.id.txv_rateCount_review)
        private val imvStar = view.findViewById<ImageView>(R.id.imv_rate_review)
        private val imvEdit = view.findViewById<ImageView>(R.id.imv_edit_review)
        private val imvDelete = view.findViewById<ImageView>(R.id.imv_delete_review)


        fun bind(
            review: Review,
            clickDelete: ClickHandlerReview,
            clickEdit: ClickHandlerReview,
        ) {
            if (review.review.isNullOrBlank()) {
                txvReview.text = "بدون نظر"
            }else{
                txvReview.text =HtmlCompat.fromHtml(review.review, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
            if (review.reviewer.isNullOrBlank()){
                txvReviewer.text = "خریدار"
            }else
                txvReviewer.text = review.reviewer

            txvRate.text = review.rating.toString()
            if (review.rating == 0){
                imvStar.setImageResource(R.drawable.ic_baseline_star_outline_24)
            }else
                imvStar.setImageResource(R.drawable.ic_baseline_star_rate_24)

            imvDelete.setOnClickListener {
                clickDelete(review)
            }
            imvEdit.setOnClickListener {
                clickEdit(review)
            }

        }



    }

}