package com.sample.com.exam.View.Dashboard

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.nttdata.com.islavelocity.Contracts.IDashboardView
import com.sample.com.exam.AppDelegate
import com.sample.com.exam.Contracts.IDashboardListener
import com.sample.com.exam.Dependency.Component.ApplicationComponent
import com.sample.com.exam.Presenter.Base.DashboardPresenter
import com.sample.com.exam.Presenter.Base.Interface.IPresenter
import com.sample.com.exam.R
import com.sample.com.exam.View.Base.ClientView
import com.sample.com.exam.ViewModel.ProductsViewModel
import kotlinx.android.synthetic.main.activity_product_listing_layout.*
import javax.inject.Inject
import android.support.v7.widget.RecyclerView
import com.sample.com.exam.View.Details.ProductDeliveryDetailsActivity

class DashboardActivity: ClientView(),
        IDashboardView,
        IDashboardListener {

    @Inject lateinit var mPresenter: DashboardPresenter
    private var mAdapter: DashboardAdapter? = null
    private var mIsLoading: Boolean = false

    private val linearLayoutManager = LinearLayoutManager(this)

    override fun getContentView(): Int {
        return R.layout.activity_product_listing_layout
    }

    override fun onLoad() {

        // set up events
        onEvents()

        // get products deliveries from API
        mPresenter.loadProducts()

    }

    private fun onEvents() {
        rv_products.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (recyclerView?.canScrollVertically(1) == false
                        && !mIsLoading) {
                    mPresenter.loadMore()
                    setIsLoading(true)
                }
            }
        })
    }

    override fun onLoadProducListing(items: MutableList<ProductsViewModel>) {
        // set up recycler view
        rv_products.layoutManager = linearLayoutManager
        mAdapter = DashboardAdapter(this, items, this)
        rv_products.adapter = mAdapter
        rv_products.isNestedScrollingEnabled = false
        setIsLoading(false)

    }

    override fun onLoadMoreProducListing(items: MutableList<ProductsViewModel>) {
        mAdapter?.addItems(items)
        setIsLoading(false)
    }

    override fun onItemClicked(item: ProductsViewModel, position: Int) {
        val id = item.Id ?: 0
        startActivity(ProductDeliveryDetailsActivity.newIntent(this, id))
    }

    override fun onFailedGettingDeliveries(message: String) {

        setIsLoading(false)
        showErrorToast(message)
    }

    private fun setIsLoading(isLoading: Boolean){
        mIsLoading = isLoading
    }

    override fun getPresenter(): IPresenter? {
        return mPresenter
    }

    override fun inject(applicationComponent: ApplicationComponent) {
        (application as AppDelegate).getComponent().inject(this)
    }
}