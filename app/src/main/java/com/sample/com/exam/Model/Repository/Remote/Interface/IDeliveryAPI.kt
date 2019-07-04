package com.sample.com.exam.Model.Repository.Local.Interface

import com.sample.com.exam.Model.Repository.Remote.ResponseModel.Products
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IDeliveryAPI {

    @GET("deliveries")
    fun getDeliveries(@Query("offset") offset: Int,
                      @Query("limit") limit: Int): Observable<List<Products>>

}