package com.sample.com.exam.ViewModel

import com.google.gson.annotations.SerializedName
import com.sample.com.exam.Model.Entity.Location
import io.realm.annotations.PrimaryKey
import java.util.*

class ProductsViewModel {
    var Id: Int? = null
    var Description: String? = null
    var ImageUrl: String? = null
    var Location: Location? = null
}