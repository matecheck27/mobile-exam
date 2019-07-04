package com.sample.com.exam.Contracts

interface IDashboardPresenter {

    fun loadProducts(isLoadMore: Boolean = false)

    fun loadMore()
}