package com.sample.com.exam.Model.Entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Location : RealmObject() {

    @SerializedName("lat")
    var Lat: Double? = null

    @SerializedName("lng")
    var Lng: Double? = null

    @SerializedName("address")
    var Address: String? = null

}