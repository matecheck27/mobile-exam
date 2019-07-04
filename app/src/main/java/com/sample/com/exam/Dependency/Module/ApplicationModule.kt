package com.sample.com.exam.Dependency.Module

import android.app.Application
import android.content.Context
import com.sample.com.exam.Model.Repository.Local.Interface.IDeliveryAPI
import com.sample.com.exam.Model.Repository.Local.ProductsData
import com.sample.com.exam.Presenter.Base.DashboardPresenter
import com.sample.com.exam.Presenter.Details.DetailsPresenter
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    private var app: Application

    constructor(application: Application) {
        this.app = application
    }

    @Provides
    fun provideContext(): Context {
        return app.applicationContext
    }

    @Provides
    fun provideDashboardPresenter(deliveryApi: IDeliveryAPI,
                                  productsData: ProductsData): DashboardPresenter {
        return DashboardPresenter(deliveryApi, productsData)
    }

    @Provides
    fun provideDetailsPresenter(productsData: ProductsData): DetailsPresenter {
        return DetailsPresenter(productsData)
    }


}

