package com.sample.com.exam.Presenter.Base

import com.nttdata.com.islavelocity.Contracts.IDashboardView
import com.sample.com.exam.Contracts.IDashboardPresenter
import com.sample.com.exam.Model.Repository.Local.Interface.IDeliveryAPI
import com.sample.com.exam.Model.Repository.Remote.ResponseModel.Products
import com.sample.com.exam.Model.Repository.Local.ProductsData
import com.sample.com.exam.Shared.MapperHelper
import com.sample.com.exam.ViewModel.ProductsViewModel

class DashboardPresenter(deliveryApi: IDeliveryAPI,
                         productsData: ProductsData) :
        ClientPresenter<IDashboardView>(), IDashboardPresenter {

    private val mDeliveryApi: IDeliveryAPI = deliveryApi
    private val mProductsData: ProductsData = productsData
    private val mTempProducts = mutableListOf<Products>()

    private var mPaginationOffset = 0
    private var paginationLimit = 5

    override fun loadProducts(isLoadMore: Boolean) {

        buildAPI(mDeliveryApi.getDeliveries(mPaginationOffset,
                paginationLimit), {

            mProductsData.insertOrUpdateList(it)
            updateUI(it, isLoadMore)

        }, {

            val data = mProductsData.getAllItems() ?: mutableListOf()
            updateUI(data, isLoadMore)
            getView()?.onFailedGettingDeliveries(it.localizedMessage)
        })

    }

    private fun updateUI(it: List<Products>, isLoadMore: Boolean) {

        // convert product model to view model using map
        val mappedViewModel = it.map {
            MapperHelper.ConvertModelTo(it, ProductsViewModel::class.java)
        }.toMutableList()

        // update UI
        when(!isLoadMore){
            true -> {
                getView()?.onLoadProducListing(mappedViewModel)
            }
            else -> {
                getView()?.onLoadMoreProducListing(mappedViewModel)
            }
        }
    }



    override fun loadMore() {
        mPaginationOffset += paginationLimit
        loadProducts(true)
    }

}