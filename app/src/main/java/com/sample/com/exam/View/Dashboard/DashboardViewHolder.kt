package com.sample.com.exam.View.Dashboard

import android.content.Context
import android.media.Image
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.sample.com.exam.Contracts.IDashboardListener
import com.sample.com.exam.R
import com.sample.com.exam.ViewModel.ProductsViewModel
import kotlinx.android.synthetic.main.row_delivery_layout.view.*

class DashboardViewHolder(context: Context,
                          itemView: View,
                          val listener: IDashboardListener) : RecyclerView.ViewHolder(itemView) {

    private var mContext: Context? = null

    private var mContainer: ConstraintLayout? = itemView.ll_container
    private var mDescription: TextView? = itemView.tv_description
    private var mAddress: TextView? = itemView.tv_address
    private var mProductImage: ImageView? = itemView.iv_product_image

    init {
        // context
        mContext = context

    }

    fun bindDataToViewHolder(item: ProductsViewModel, position: Int) {
        setItem(item, position)
    }

    private fun setItem(item: ProductsViewModel, position: Int) {
        val desc = "${item.Description} ${getSeparator()}"
        mDescription?.text =  desc
        mAddress?.text = item.Location?.Address

        val photo = item.ImageUrl

        Glide.with(mContext)
                .load(photo)
                .priority(Priority.HIGH)
                .placeholder(R.drawable.ic_image)
                .into(mProductImage)

        mContainer?.setOnClickListener {
            listener.onItemClicked(item, position)
        }
    }

    private fun getSeparator(): String? {
        return mContext?.getString(R.string.label_separator)
    }
}