package com.sample.com.exam.Presenter.Details

import com.sample.com.exam.Contracts.Details.IDetailsPresenter
import com.sample.com.exam.Contracts.Details.IDetailsView
import com.sample.com.exam.Model.Repository.Remote.ResponseModel.Products
import com.sample.com.exam.Model.Repository.Local.ProductsData
import com.sample.com.exam.Presenter.Base.ClientPresenter
import com.sample.com.exam.Shared.Constants.Companion.DATA_ID
import com.sample.com.exam.Shared.MapperHelper
import com.sample.com.exam.ViewModel.ProductsViewModel

class DetailsPresenter(productsData: ProductsData): ClientPresenter<IDetailsView>(),
        IDetailsPresenter {

    private var mProductsData = productsData

    override fun loadProductDetail(id: Int) {
        val item = mProductsData.queryFirst(DATA_ID, id)

        val mappedModel =
                MapperHelper.ConvertModelTo(item, ProductsViewModel::class.java)

        getView()?.onLoadItem(mappedModel)
    }

    override fun updateItem(item: ProductsViewModel) {
        val mappedModel =
                MapperHelper.ConvertModelTo(item, Products::class.java)
        mProductsData.insertOrUpdate(mappedModel)
    }
}