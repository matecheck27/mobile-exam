package com.sample.com.exam.Contracts

import com.sample.com.exam.ViewModel.ProductsViewModel

interface IDashboardListener {

    fun onItemClicked(item: ProductsViewModel, position: Int)
}