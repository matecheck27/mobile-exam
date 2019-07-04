package com.sample.com.exam.Contracts.Details

import com.sample.com.exam.ViewModel.ProductsViewModel

interface IDetailsView {

    fun onLoadItem(item: ProductsViewModel)
}