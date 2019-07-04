package com.nttdata.com.islavelocity.Contracts

import com.sample.com.exam.ViewModel.ProductsViewModel

interface IDashboardView {

    fun onLoadProducListing(items: MutableList<ProductsViewModel>)

    fun onLoadMoreProducListing(items: MutableList<ProductsViewModel>)

    fun onFailedGettingDeliveries(message: String)
}