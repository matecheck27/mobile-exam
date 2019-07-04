package com.sample.com.exam.Model.Repository.Local

import com.sample.com.exam.Model.Repository.Remote.ResponseModel.Products
import com.sample.com.exam.Model.Repository.Local.Base.BaseRealm
import com.sample.com.exam.Model.Repository.Local.Base.IBaseData
import com.sample.com.exam.Shared.Constants.Companion.DATA_ID
import io.realm.Realm
import io.realm.RealmModel

class ProductsData (var mRealm: Realm) : BaseRealm(mRealm), IBaseData<Products> {

    override fun insert(t: Products) {
        insertOrUpdate(t)
    }

    override fun update(t: Products) {
        Replace(t, Products::class.java)
    }

    override fun delete(t: Products) {
        Delete(Products::class.java, DATA_ID, t.Id.toString())
    }

    override fun deleteAll() {
        DeleteAll(Products::class.java)
    }

    override fun getAll(): List<Products>? {
        return GetAll(Products::class.java)
    }

    fun getAllItems(): List<Products>? {
        return GetAll(Products::class.java)
    }

    override fun getFirst(): Products? {
        return GetFirst(Products::class.java)
    }

    override fun queryAll(fieldName: String, value: String): List<Products> {
        return Query(Products::class.java, fieldName, value)
    }

    override fun queryFirst(fieldName: String, value: Int): Products {
        return getFirst(Products::class.java, fieldName, value)
    }

    protected fun <T> getFirst(clazz: Class<out RealmModel>, fieldName: String, value: Int) : T {
        val item = mRealm.where(clazz).equalTo(fieldName, value).findFirst()

        if(item != null) {
            return mRealm.copyFromRealm(item) as T
        }
        return item as T
    }

    fun insertOrUpdate(t: Products) {
        mRealm.executeTransaction { realm ->
            realm.insertOrUpdate(t)
        }
    }

    fun insertOrUpdateList(t: Collection<Products>) {
        mRealm.executeTransaction { realm ->
            realm.insertOrUpdate(t)
        }
    }

}