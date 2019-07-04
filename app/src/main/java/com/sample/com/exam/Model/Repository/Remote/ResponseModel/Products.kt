package com.sample.com.exam.Model.Repository.Remote.ResponseModel

import com.google.gson.annotations.SerializedName
import com.sample.com.exam.Model.Entity.Location
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Products : RealmObject()  {

    @PrimaryKey
    @SerializedName("id")
    var Id: Int? = null

    @SerializedName("description")
    var Description: String? = null

    @SerializedName("imageUrl")
    var ImageUrl: String? = null

    @SerializedName("location")
    var Location: Location? = null

}



