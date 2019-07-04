package com.sample.com.exam.View.Dashboard

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sample.com.exam.Contracts.IDashboardListener
import com.sample.com.exam.R
import com.sample.com.exam.ViewModel.ProductsViewModel

class DashboardAdapter (val context: Context,
                        val list: MutableList<ProductsViewModel>,
                        val listener: IDashboardListener) :
        RecyclerView.Adapter<DashboardViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DashboardViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.row_delivery_layout, parent, false)
        return DashboardViewHolder(context, view, listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder?, position: Int) {
        holder?.bindDataToViewHolder(list[position], position)
    }


    fun addItems(items: MutableList<ProductsViewModel>){
        list.addAll(items)
        notifyDataSetChanged()
    }

}