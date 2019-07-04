package com.sample.com.exam.Dependency.Component

import com.sample.com.exam.Dependency.Module.ApiModule
import com.sample.com.exam.Dependency.Module.ApplicationModule
import com.sample.com.exam.Dependency.Module.DataModule
import com.sample.com.exam.View.Dashboard.DashboardActivity
import com.sample.com.exam.View.Details.ProductDeliveryDetailsActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(ApplicationModule::class),(DataModule::class), (ApiModule::class)])
interface ApplicationComponent {

    fun inject(dashboardActivity: DashboardActivity)

    fun inject(deliveryDetailsActivity: ProductDeliveryDetailsActivity)
}