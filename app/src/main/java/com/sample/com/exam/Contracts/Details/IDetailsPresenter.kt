package com.sample.com.exam.Contracts.Details

import com.sample.com.exam.ViewModel.ProductsViewModel

interface IDetailsPresenter {
    fun loadProductDetail(id: Int)

    fun updateItem(item: ProductsViewModel)
}